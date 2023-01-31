package com.edugobeti.gerenciamentodepessoas;

//import com.edugobeti.gerenciamentodepessoas.service.DataBaseSevice;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiGerenciamentoPessoaDesafioJavaApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ApiGerenciamentoPessoaDesafioJavaApplication.class, args);
	}

		//@Autowired
		//private DataBaseSevice dbSevice;
	
		@Override
		public void run(String... args) throws Exception {
			//dbSevice.InstanciamentoDB();
		}

}
