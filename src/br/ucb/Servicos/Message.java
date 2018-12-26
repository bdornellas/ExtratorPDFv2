package br.ucb.Servicos;

import javax.swing.JOptionPane;

public class Message {
	
	//Mensagem com ícone de Erro.(X)
	public static void mensagemErro(String frase, String titulo){
		JOptionPane.showMessageDialog(null, frase,titulo,JOptionPane.ERROR_MESSAGE);
	}
	
	//Mensagem com ícone de Alerta.(!)
	public static void mensagemAlerta(String frase, String titulo){
		JOptionPane.showMessageDialog(null, frase,titulo,JOptionPane.WARNING_MESSAGE);
	}
	
	//Mensagem com ícone de Informação.(I)
	public static void mensagemInformacao(String frase, String titulo){
		JOptionPane.showMessageDialog(null, frase,titulo,JOptionPane.INFORMATION_MESSAGE);
	}
	
	//Mensagem com ícone de Interrogação.(?)
	public static void mensagemQuestao(String frase, String titulo){
		JOptionPane.showMessageDialog(null, frase,titulo,JOptionPane.QUESTION_MESSAGE);
	}
	
	//Mensagem sem ícone
	public static void mensagemLimpa(String frase, String titulo){
		JOptionPane.showMessageDialog(null, frase,titulo,JOptionPane.PLAIN_MESSAGE);
	}
	
	//Janela de diálogo que captura e retorna a String informada pelo usuário
	public static String getMensagemUsuario(String frase, String titulo){
		String dado;
		do{
		     dado=JOptionPane.showInputDialog(null,frase,titulo,JOptionPane.PLAIN_MESSAGE);
		}while(dado==null | dado.equals("-1"));//enquanto o usuário não informar nada ou o usuário clicar no botão CLOSE do OS
		return dado;
	}
	
	//Janela que oferece opções "SIM" e "NÃO". Retorna TRUE se o usuário escolhe opção "SIM" e FALSE para "NÃO"
	public static boolean continuarOperacao(String frase,String titulo){
		String opcoes[]={"SIM","NÃO"};
		boolean resultado=false;
		int valor=0;
		do{
			valor=JOptionPane.showOptionDialog(null, frase, titulo, 0,JOptionPane.QUESTION_MESSAGE, null,opcoes, opcoes[0]);
		}while(valor==(-1));
		if(valor==0){
			resultado=true;
		}
		return resultado;
	}

}
