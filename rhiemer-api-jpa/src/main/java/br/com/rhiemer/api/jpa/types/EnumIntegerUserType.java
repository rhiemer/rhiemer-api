package br.com.rhiemer.api.jpa.types;

import java.sql.Types;

/**
 * UserType Integer para Enumerações.
 * <p>
 * Tipo extendido do Hibernate para persistir constantes de Enumerações do tipo Integer. <br>
 * Toda Enumeração que persistir deve implementar o método <code>public int getCodigo()</code>.
 * 
 * @param <E> Tipo de enumeração que será persistido pela classe
 */
public class EnumIntegerUserType<E extends Enum<E>> extends EnumNumberUserType<E> {

  
    @Override
    public int getSQLType() {
        return Types.INTEGER;
    }
}