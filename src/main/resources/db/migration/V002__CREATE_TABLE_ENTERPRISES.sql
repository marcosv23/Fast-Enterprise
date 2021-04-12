CREATE TABLE enterprises (
  id BIGINT NOT NULL AUTO_INCREMENT UNIQUE, 
  user_id BIGINT,
  fullname VARCHAR(80), 
  cnpj CHAR(14) NOT NULL UNIQUE, 
  company_email VARCHAR(50),
  phone VARCHAR(20), 
  foundation DATE,
  owner VARCHAR(50),
  situation VARCHAR(30),
  neighborhood  VARCHAR(150),
  local_number  VARCHAR(100),
  city VARCHAR(150),
  street VARCHAR(150),
  size_company VARCHAR(150),
  registered_by VARCHAR(80),
  created_at TIMESTAMP DEFAULT  CURRENT_TIMESTAMP, 
  updated_at TIMESTAMP DEFAULT   CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, 
  deleted_at TIMESTAMP NULL DEFAULT NULL,
  
  CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES users(id),
  primary key (id)
);
