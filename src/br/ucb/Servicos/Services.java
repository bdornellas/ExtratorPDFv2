package br.ucb.Servicos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import br.ucb.Beans.ArquivoPDF;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

public class Services {

	public static String consertaArquivo(String dado){
		StringBuilder temp=new StringBuilder(dado);
		StringBuilder resultado=new StringBuilder();
		for(int aux=0;aux<dado.length();aux++){
			if(temp.charAt(aux)=='@' && temp.charAt(aux+1)=='#'){
				do{
					if(aux<dado.length()){
						aux++;
					}
					else{
						return "ERRO";
					}
				}while(temp.charAt(aux)=='#' && temp.charAt(aux+1)=='@');
				aux=aux++;
			}
			resultado.append(dado.charAt(aux));
		}
		temp=new StringBuilder(resultado);
		resultado= new StringBuilder();
		for(int aux=0;aux<dado.length();aux++){
			if(!(temp.charAt(aux)==' ')){
				resultado.append(temp.charAt(aux));
			}
		}
		return temp.toString();
	}

	public static boolean validaDiretorio(String dado){
		if(dado.endsWith(".pdf")){
			return true;
		}
		return false;
	}

	public static String getOS(){
		return System.getProperty("os.name");
	}

	public static boolean isDigito(char dado){
		String valores="0123456789";
		for(int aux=0;aux<valores.length();aux++){
			if(dado==valores.charAt(aux)){
				return true;
			}
		}
		return false;
	}

	public static boolean isCaractere(char dado){
		String valores="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for(int aux=0;aux<valores.length();aux++){
			if(dado==valores.charAt(aux)){
				return true;
			}
		}
		return false;
	}

	public static boolean isSimbolo(char dado){
		String valores=";,:()<>-=+*//@#";
		for(int aux=0;aux<valores.length();aux++){
			if(dado==valores.charAt(aux)){
				return true;
			}
		}
		return false;
	}

	public static String removeEspacos(String dado){
		StringBuilder correcao=new StringBuilder();
		for(int aux=0;aux<dado.length();aux++){
			if(dado.charAt(aux)!=' '){
				correcao.append(dado.charAt(aux));
			}
		}
		return correcao.toString();
	}

	public static String removeEnter(String dado){
		StringBuilder correcao=new StringBuilder();
		for(int aux=0;aux<dado.length();aux++){
			if(dado.charAt(aux)!='\n'){
				correcao.append(dado.charAt(aux));
			}
		}
		return correcao.toString();
	}

	/*public static String consertaString(String dado){
		if(dado!=null){
			dado=removeEnter(dado);
			dado=removeEspacos(dado);
		}
		else{
			return null;
		}
		return dado;
	}*/

	public static String extraiConteudoPDF(String diretorio){
		String conteudo=null;
		try{
			PdfReader reader = new PdfReader(diretorio);
			conteudo=PdfTextExtractor.getTextFromPage(reader, 1);
			reader.close();
		}catch(IOException exception){
			Message.mensagemErro("Arquivo em uso ou corrompido", "Erro");
			conteudo=null;
		}
		return conteudo;
	}

	public static String extraiNomeAR(String conteudo){
		int index=0;
		String dado=conteudo;
		StringBuilder resultado=new StringBuilder();
		do{
			if((dado.charAt(index)=='j' || dado.charAt(index)=='J') && (dado.charAt(index+1)=='C' || dado.charAt(index+1)=='c')){
				resultado.append(dado.charAt(index));
				index++;
				resultado.append(dado.charAt(index));
				index++;
				do{
					resultado.append(dado.charAt(index));
					index++;
				}while(isDigito(dado.charAt(index)));
				if((dado.charAt(index)=='B' || dado.charAt(index)=='b') &&(dado.charAt(index+1)=='R' || dado.charAt(index+1)=='r')){
					resultado.append(dado.charAt(index));
					resultado.append(dado.charAt(index+1));
					break;
				}
			}
			else{
				index++;
			}
		}while(index<dado.length()-3);
		return resultado.toString();
	}
	
	public static String extraiAgencia(String conteudo){
		int index=0;
		String dado=conteudo;
		StringBuilder resultado=new StringBuilder();
		do{
			if((dado.charAt(index)=='A' || dado.charAt(index)=='a') && (dado.charAt(index+1)==' ') && (isDigito(dado.charAt(index+2)))){
				index=index+2;
				while(isDigito(dado.charAt(index))){
					resultado.append(dado.charAt(index));
					index++;
				};
				break;
			}
			index++;
		}while(index<dado.length()-4);
		return resultado.toString();
	}
	
	public static String extraiOperacao(String conteudo){
		int index=0;
		String dado=conteudo;
		StringBuilder resultado=new StringBuilder();
		do{
			if((dado.charAt(index)=='O' || dado.charAt(index)=='o') && (dado.charAt(index+1)==' ') && (isDigito(dado.charAt(index+2)))){
				index=index+2;
				while(isDigito(dado.charAt(index))){
					resultado.append(dado.charAt(index));
					index++;
				};
				break;
			}
			index++;
		}while(index<dado.length()-4);
		return resultado.toString();
	}

	public static boolean renomear(File arquivo,String novoNome){
		boolean success=false;
		StringBuilder novo=new StringBuilder();
		String diretorio=arquivo.getParent();
		for(int index=0;index<diretorio.length();index++){
			if(diretorio.charAt(index)=='\\'){
				novo.append("\\");
				novo.append("\\");
			}
			else{
				novo.append(diretorio.charAt(index));
			}
		}
		success = arquivo.renameTo(new File((novo.toString())+"\\\\"+novoNome));
		return success;
	}

	public static String extraiNomeDoArquivo(String diretorio){
		StringBuilder nome= new StringBuilder();
		int index=diretorio.length()-1;
		do{
			nome.append(diretorio.charAt(index));
			index--;
		}while(diretorio.charAt(index)!='\\');
		String resultado=nome.toString();
		resultado=reverse(resultado);
		return resultado;
	}

	public static String extraiDiretorioDoArquivo(String diretorio){
		StringBuilder source= new StringBuilder();
		int limite=diretorio.length()-1;
		do{
			limite--;
		}while(diretorio.charAt(limite)!='\\');
		for(int aux=0;aux<=limite;aux++){
			source.append(diretorio.charAt(aux));
		}
		String resultado=source.toString();
		return resultado;
	}

	public static String reverse(String dado){
		StringBuilder novo=new StringBuilder();
		for(int index=dado.length()-1;index>=0;index--){
			novo.append(dado.charAt(index));
		}
		return novo.toString();
	}

	public static boolean dividirPDF(ArquivoPDF arqPDF,Integer paginaInicial,Integer paginaFinal,Integer paginasPorArquivo){
		boolean resultado=false;
		String diretorio=arqPDF.getArquivo().getAbsolutePath();
		try {
			PdfReader reader = new PdfReader(diretorio);
			int totalPaginas;
			if(paginaFinal==null){
				totalPaginas= reader.getNumberOfPages();
			}
			else{
				totalPaginas=paginaFinal;
			}
			int index = paginaInicial;            
			while ( index <= totalPaginas ) {
				String outFile = (arqPDF.getArquivo().getAbsolutePath())+String.format("%03d", index) + ".pdf"; 
				Document document = new Document(reader.getPageSizeWithRotation(1));
				PdfCopy writer = new PdfCopy(document, new FileOutputStream(outFile));
				document.open();
				for(int aux=1;aux<=paginasPorArquivo;aux++){
					if(index>totalPaginas){
						break;
					}
					PdfImportedPage page = writer.getImportedPage(reader, index);
					writer.addPage(page);
					index++;
				}
				document.close();
				writer.close();
			}
			reader.close();
			resultado=true;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return resultado;
	}
		
	public static int extraiQtdePaginas(ArquivoPDF arqPDF){
		String diretorio=arqPDF.getArquivo().getAbsolutePath();
		int totalPaginas=0;
		try {
			PdfReader reader = new PdfReader(diretorio);
			totalPaginas= reader.getNumberOfPages();
			reader.close();
		} 
		catch (Exception e) {
			totalPaginas=0;
		}
		return totalPaginas;
	}
}