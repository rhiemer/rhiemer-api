DELIMITER $$

DROP PROCEDURE IF EXISTS add_email_address_column_to_customers_table $$

-- Create the stored procedure to perform the migration
CREATE PROCEDURE add_email_address_column_to_customers_table()

BEGIN
  
  -- Add the email_address column to the customers table, if it doesn't already exist
  IF NOT EXISTS ((SELECT * FROM information_schema.columns WHERE table_schema=DATABASE() AND table_name='customers' AND column_name='email_address')) THEN
    ALTER TABLE customers ADD email_address VARCHAR(256);
  END IF;

END $$

-- Execute the stored procedure
CALL add_email_address_column_to_customers_table() $$

-- Don't forget to drop the stored procedure when you're done!
DROP PROCEDURE IF EXISTS add_email_address_column_to_customers_table $$
 
DELIMITER ;