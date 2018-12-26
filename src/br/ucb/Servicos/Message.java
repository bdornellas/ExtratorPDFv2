package br.ucb.Servicos;

import javax.swing.JOptionPane;

public class Message {
	
	public static void mensagemErro(String frase, String titulo){
		JOptionPane.showMessageDialog(null, frase,titulo,JOptionPane.ERROR_MESSAGE);
	}
	
	public static void mensagemAlerta(String frase, String titulo){
		JOptionPane.showMessageDialog(null, frase,titulo,JOptionPane.WARNING_MESSAGE);
	}
	
	public static void mensagemInformacao(String frase, String titulo){
		JOptionPane.showMessageDialog(null, frase,titulo,JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void mensagemQuestao(String frase, String titulo){
		JOptionPane.showMessageDialog(null, frase,titulo,JOptionPane.QUESTION_MESSAGE);
	}
	
	public static void mensagemLimpa(String frase, String titulo){
		JOptionPane.showMessageDialog(null, frase,titulo,JOptionPane.PLAIN_MESSAGE);
	}
	
	public static String getMensagemUsuario(String frase, String titulo){
		String dado;
		do{
		     dado=JOptionPane.showInputDialog(null,frase,titulo,JOptionPane.PLAIN_MESSAGE);
		}while(dado==null | dado.equals("-1"));
		return dado;
	}
	
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
