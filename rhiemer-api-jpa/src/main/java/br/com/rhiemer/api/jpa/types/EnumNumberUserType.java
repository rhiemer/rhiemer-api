package br.com.rhiemer.api.jpa.types;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.internal.util.compare.EqualsHelper;
import org.hibernate.type.IntegerType;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;


import br.com.rhiemer.api.jpa.enums.EnumeracaoComCodigoDescricao;

public abstract class EnumNumberUserType<E extends Enum<E>> implements UserType, ParameterizedType {

    private Class<E> clazz;

    private static final IntegerType INTEGER = new IntegerType();

    /** {@inheritDoc} */
    @Override
    @SuppressWarnings("unchecked")
    public void setParameterValues(Properties params) {
        String enumClassName = params.getProperty("enumClass");
        if (enumClassName == null) {
            throw new MappingException("enumClassName parameter not specified");
        }
        try {
            this.clazz = (Class<E>) Class.forName(enumClassName);
        } catch (java.lang.ClassNotFoundException e) {
            throw new MappingException("enumClass " + enumClassName + " not found", e);
        }
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return EqualsHelper.equals(x, y);
    }

    /** {@inheritDoc} */
    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    /** {@inheritDoc} */
    @Override
    public Object assemble(Serializable cache, Object owner) throws HibernateException {
        return cache;
    }

    /** {@inheritDoc} */
    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isMutable() {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
            throws HibernateException, SQLException {
        Object codigo = INTEGER.nullSafeGet(rs, names[0], session);
        return codigo == null ? null : getEnum((Integer) codigo);
    }

    /**
     * Método que retorna o código das constantes.
     * 
     * @param codigo
     * @return
     */
    private EnumeracaoComCodigoDescricao getEnum(Integer codigo) {
        Class<E> enumClass = returnedClass();
        Enum<E>[] enums = enumClass.getEnumConstants();
        for (int i = 0; i < enums.length; i++) {
            if (((EnumeracaoComCodigoDescricao) enums[i]).getCodigo().equals(codigo)) {
                return (EnumeracaoComCodigoDescricao) enums[i];
            }
        }
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public void nullSafeSet(PreparedStatement pstm, Object value, int index,
            SessionImplementor session) throws HibernateException, SQLException {
        if (value == null) {
            pstm.setNull(index, getSQLType());
        } else {
            Integer codigoEnum = ((EnumeracaoComCodigoDescricao) value).getCodigo();
            pstm.setInt(index, codigoEnum);
        }
    }

    /** {@inheritDoc} */
    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }

    /** {@inheritDoc} */
    @Override
    public Class<E> returnedClass() {
        return clazz;
    }

    /** {@inheritDoc} */
    @Override
    public int[] sqlTypes() {
        return getSQLTypes().clone();
    }

    public abstract int getSQLType();

    public int[] getSQLTypes() {
        return new int[] {getSQLType()};
    };
}
