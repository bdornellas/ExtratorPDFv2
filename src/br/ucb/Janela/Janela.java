package br.ucb.Janela;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import br.ucb.Beans.ArquivoPDF;
import br.ucb.Servicos.Message;
import br.ucb.Servicos.Services;

public class Janela implements MouseListener{

	//SWING JANELA PRINCIPAL
	private JFrame janela;
	private JPanel painelPrincipal,painelDetalhes,painelDiretorio,painelArquivosAbertos,painelTexto;
	private JButton abrirArquivosButton;
	private JTextField diretorio;
	private JLabel titulo,labelQtdeArquivos;
	private JTextArea texto;
	private JScrollPane scroll;
	private JMenuBar menu;
	private JMenu inicio,opcoes,ajuda,executar;
	private JMenuItem abrirArquivosMenu,sair,descartarArquivos,dividirArquivos,configuracoes,checarConteudo,renomearArquivos,listarArquivos,sobre;
	JFileChooser janelaDiretorio;

	//SWING JANELA DE CONFIGURAÇÕES
	private JFrame janelaConfiguracoes;
	private JLabel labelPaginasPorArquivo,labelPaginaInicial,labelPaginaFinal;
	private JTextField textoPaginasPorArquivo,textoPaginaInicial,textoPaginaFinal;
	private JButton salvarConfiguracoes,cancelarConfiguracoes;
	private JPanel painel1,painel2;

	//VARIÁVEIS
	final String ARQUIVOSABERTOS="Arquivos abertos: ";
	final String TITULO="Extrator PDF";
	private ArrayList<ArquivoPDF> listaArquivosPDF;//Lista de arquivos PDF
	private Integer paginasPorArquivo;
	private Integer paginaInicial;
	private Integer paginaFinal;

	//Método para criar a janela principal
	public void criajanela(){
		janela=new JFrame();
		menu=new JMenuBar();
		inicio=new JMenu("Inicio");
		opcoes=new JMenu("Opções");
		ajuda=new JMenu("Informações");
		executar=new JMenu("Executar");
		abrirArquivosMenu=new JMenuItem("Abrir arquivos");
		sair=new JMenuItem("Sair");
		descartarArquivos=new JMenuItem("Fechar arquivos");
		descartarArquivos.setEnabled(false);
		dividirArquivos=new JMenuItem("1 - Dividir PDF");
		dividirArquivos.setEnabled(false);
		configuracoes=new JMenuItem("Configurações");
		configuracoes.setEnabled(false);
		checarConteudo=new JMenuItem("Visualizar conteúdo");
		checarConteudo.setEnabled(false);
		renomearArquivos=new JMenuItem("2 - Renomear arquivos");
		renomearArquivos.setEnabled(false);
		listarArquivos=new JMenuItem("Listar arquivos abertos");
		listarArquivos.setEnabled(false);
		sobre=new JMenuItem("Sobre o sistema");

		abrirArquivosMenu.addMouseListener(this);
		sair.addMouseListener(this);
		descartarArquivos.addMouseListener(this);
		dividirArquivos.addMouseListener(this);
		configuracoes.addMouseListener(this);
		checarConteudo.addMouseListener(this);
		renomearArquivos.addMouseListener(this);
		listarArquivos.addMouseListener(this);
		sobre.addMouseListener(this);

		inicio.add(abrirArquivosMenu);
		inicio.add(descartarArquivos);
		inicio.addSeparator();
		inicio.add(sair);
		executar.add(dividirArquivos);
		executar.add(renomearArquivos);
		opcoes.add(configuracoes);
		opcoes.addSeparator();
		opcoes.add(listarArquivos);
		opcoes.add(checarConteudo);
		ajuda.add(sobre);

		menu.add(inicio);
		menu.add(executar);
		menu.add(opcoes);
		menu.add(ajuda);
		janela.setJMenuBar(menu);

		texto=new JTextArea(29,70);
		texto.setEditable(false);
		painelPrincipal=new JPanel();
		painelDetalhes=new JPanel();
		//painelDetalhes.setLayout(new GridLayout(1,2,2,2));
		painelDiretorio=new JPanel();
		painelArquivosAbertos=new JPanel();
		painelTexto=new JPanel();
		scroll=new JScrollPane(texto);
		diretorio=new JTextField(45);
		diretorio.setEditable(false);
		titulo=new JLabel("Diretório:");
		labelQtdeArquivos=new JLabel(ARQUIVOSABERTOS+"0");
		labelQtdeArquivos.addMouseListener(this);
		labelQtdeArquivos.setToolTipText("Clique para visualizar os arquivos abertos");
		abrirArquivosButton=new JButton("...");
		abrirArquivosButton.addMouseListener(this);
		painelTexto.add(scroll);
		painelDiretorio.add(titulo);
		painelDiretorio.add(diretorio);
		painelDiretorio.add(abrirArquivosButton);
		painelArquivosAbertos.add(labelQtdeArquivos);
		painelDetalhes.add(painelDiretorio);
		painelDetalhes.add(painelArquivosAbertos);
		painelPrincipal.add(painelDetalhes);
		painelPrincipal.add(painelTexto);
		janela.add(painelPrincipal);
		janela.setSize(800,600);
		janela.setResizable(false);
		janela.setTitle(TITULO+" - "+Services.getOS());
		janela.setVisible(true);
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		centraliza(janela);

		this.listaArquivosPDF=new ArrayList<ArquivoPDF>();
		this.paginaInicial=1;
		this.paginasPorArquivo=2;
		this.paginaFinal=null;

	}

	//Método para criar a janela de configurações
	private void criaJanelaConfiguracoes(){
		janelaConfiguracoes=new JFrame();
		janelaConfiguracoes.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
		janelaConfiguracoes.setTitle(TITULO);
		labelPaginaInicial=new JLabel("A partir da página: ");
		labelPaginasPorArquivo=new JLabel("Páginas por arquivo: ");
		textoPaginaInicial=new JTextField(this.paginaInicial.toString());
		textoPaginaInicial.setColumns(3);
		textoPaginaInicial.setToolTipText("Informe a página do arquivo para começar a divisão");
		textoPaginasPorArquivo=new JTextField(this.paginasPorArquivo.toString());
		textoPaginasPorArquivo.setColumns(3);
		textoPaginasPorArquivo.setToolTipText("Informe quantas páginas cada arquivo deve ter");
		textoPaginaFinal=new JTextField();
		textoPaginaFinal.setColumns(3);
		textoPaginaFinal.setToolTipText("Dica: deixe em branco para dividir até o final do arquivo.");
		labelPaginaFinal=new JLabel("Até a página: ");
		salvarConfiguracoes=new JButton("Salvar");
		salvarConfiguracoes.addMouseListener(this);
		cancelarConfiguracoes=new JButton("Cancelar");
		cancelarConfiguracoes.addMouseListener(this);
		painel1=new JPanel();	
		painel1.setLayout(new GridLayout(3, 2));
		painel2=new JPanel();
		painel2.setLayout(new GridLayout(1, 2, 40, 30));
		painel1.add(labelPaginaInicial);
		painel1.add(textoPaginaInicial);
		painel1.add(labelPaginasPorArquivo);
		painel1.add(textoPaginasPorArquivo);
		painel1.add(labelPaginaFinal);
		painel1.add(textoPaginaFinal);
		painel2.add(cancelarConfiguracoes);
		painel2.add(salvarConfiguracoes);
		janelaConfiguracoes.add(painel1);
		janelaConfiguracoes.add(painel2);
		janelaConfiguracoes.setSize(280, 140);
		janelaConfiguracoes.setVisible(true);
		janelaConfiguracoes.setResizable(false);
		centraliza(janelaConfiguracoes);

	}

	//Método que centraliza a janela passada como parâmetro
	public static void centraliza(JFrame janela){
		Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
		janela.setLocation((tela.width-janela.getSize().width)/2,(tela.height-janela.getSize().height)/2);  
	}

	private void descartar(){
		diretorio.setText("");
		texto.setText("");
		listaArquivosPDF=new ArrayList<ArquivoPDF>();
		labelQtdeArquivos.setText(ARQUIVOSABERTOS+listaArquivosPDF.size());
		descartarArquivos.setEnabled(false);
		renomearArquivos.setEnabled(false);
		checarConteudo.setEnabled(false);
		dividirArquivos.setEnabled(false);
		configuracoes.setEnabled(false);
		listarArquivos.setEnabled(false);
	}

	private void checarConteudo(){
		boolean sucess=true;
		texto.setText("");
		texto.append("Checando conteúdo dos arquivos:\n\n");
		for(ArquivoPDF arquivoPDF:listaArquivosPDF){
			String nomeAntigo=arquivoPDF.getArquivo().getName();
			String nomeFinal;
			String nomeAR=Services.extraiNomeAR(arquivoPDF.getConteudo());
			String nomeAG=Services.extraiAgencia(arquivoPDF.getConteudo());
			String nomeOP=Services.extraiOperacao(arquivoPDF.getConteudo());
			if(!nomeAR.equals("") && !nomeAG.equals("") && !nomeOP.equals("")){//Se conseguiu extrair os dados necessário
				nomeFinal=nomeAR+" - "+nomeAG+" - "+nomeOP+".pdf";
				texto.append(nomeAntigo+" -> "+nomeFinal);
			}
			else{//Se algum dos dados falhou ao ser extraído
				texto.append(nomeAntigo+" não possui o conteúdo desejado.");
				sucess=false;
			}
			texto.append("\n");
		}
		if(sucess){
			texto.append("\nTodos os arquivos são válidos, execute a renomeação dos arquivos.");
		}
		else{
			texto.append("\nUm ou mais arquivos não possui o conteúdo desejado.");
		}
		texto.append("\n");
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getSource()==abrirArquivosButton || e.getSource()==abrirArquivosMenu){
			boolean sucess=true;
			this.janelaDiretorio = new JFileChooser();
			janelaDiretorio.setDialogTitle(TITULO);
			janelaDiretorio.setApproveButtonText("Selecionar");//Texto do botão de selecionar
			janelaDiretorio.setMultiSelectionEnabled(true);
			janelaDiretorio.setApproveButtonToolTipText("Clique para finalizar a seleção de arquivos");
			janelaDiretorio.setFileFilter(new FileNameExtensionFilter("Arquivo PDF", "pdf")); 
			janelaDiretorio.setAcceptAllFileFilterUsed(false);//Nao aceitar todas as extensões

			int retorno = janelaDiretorio.showDialog(janela, null);//Mostra a janela e captura qual botão foi clicado  
			if (retorno == JFileChooser.APPROVE_OPTION) {//Se clicou no botão de SELECIONAR   
				File [] files=janelaDiretorio.getSelectedFiles();//Array contém lista de arquivos selecionados
				listaArquivosPDF=new ArrayList<ArquivoPDF>();
				for(int aux=0;aux<files.length;aux++){
					try{
						ArquivoPDF temp=new ArquivoPDF();
						temp.setArquivo(files[aux]);
						temp.setConteudo(Services.extraiConteudoPDF(temp.getArquivo().getAbsolutePath()));
						listaArquivosPDF.add(temp);
					}
					catch(NoClassDefFoundError error){
						sucess=false;
					}
				}
				labelQtdeArquivos.setText(ARQUIVOSABERTOS+listaArquivosPDF.size());
				diretorio.setText(files[0].getParent());
				files=null;
				descartarArquivos.setEnabled(true);
				renomearArquivos.setEnabled(true);
				checarConteudo.setEnabled(true);
				dividirArquivos.setEnabled(true);
				configuracoes.setEnabled(true);
				listarArquivos.setEnabled(true);
				if(this.listaArquivosPDF.size()>1){
					checarConteudo();
					dividirArquivos.setEnabled(false);
				}
				else{
					dividirArquivos.setEnabled(true);
				}
				if(!sucess){
					Message.mensagemAlerta("Um ou mais arquivos não é legível", TITULO);
				}
			}
		}
		if(e.getSource()==descartarArquivos && descartarArquivos.isEnabled()){//Se o usuário deseja fechar os arquivos abertos
			if(this.listaArquivosPDF.size()>0){//Se houver arquivos abertos	
				descartar();
			}
			else{//Se não houver arquivos abertos
				Message.mensagemAlerta("Nenhum arquivo aberto no momento", "Atenção");
			}
		}
		if(e.getSource()==sair){//Se o usuário clica em SAIR
			System.exit(0);
		}
		if(e.getSource()==dividirArquivos && dividirArquivos.isEnabled()){//Se o usuário quer dividir um PDF
			boolean resultado;
			texto.setText("");
			texto.append("Aguarde, arquivos em análise no momento...\n\n");
			for(ArquivoPDF temp:listaArquivosPDF){//Percorre a lsita de arquivos PDF
				resultado=false;
				texto.append(temp.getArquivo().getAbsolutePath()+" em análise\n");
				resultado=Services.dividirPDF(temp, this.paginaInicial,this.paginaFinal, this.paginasPorArquivo);
				if(resultado){
					texto.append(temp.getArquivo().getAbsolutePath()+" CONCUÍDO\n");
				}
				else{
					texto.append(temp.getArquivo().getAbsolutePath()+" ERRO\n");
				}
				texto.append("\n");
			}
			Message.mensagemInformacao("Divisão de arquivos concluída", TITULO);
			descartar();
		}
		if(e.getSource()==renomearArquivos && renomearArquivos.isEnabled()){//Se o usuário deseja renomear os arquivos
			boolean endSucess=true;
			boolean sucess=false;//Flag que indica se o processo deu certo
			texto.setText("");
			texto.append("Relatório da execução do trabalho:\n\n");
			try{
				for(ArquivoPDF arquivoPDF:listaArquivosPDF){
					String nomeAntigo=arquivoPDF.getArquivo().getName();
					String nomeFinal;
					String nomeAR=Services.extraiNomeAR(arquivoPDF.getConteudo());
					String nomeAG=Services.extraiAgencia(arquivoPDF.getConteudo());
					String nomeOP=Services.extraiOperacao(arquivoPDF.getConteudo());
					if(!nomeAR.equals("") && !nomeAG.equals("") && !nomeOP.equals("")){
						nomeFinal=nomeAR+" - "+nomeAG+" - "+nomeOP+".pdf";
						sucess=Services.renomear(arquivoPDF.getArquivo(), nomeFinal);
						if(sucess){
							texto.append(nomeAntigo+" renomeado para "+nomeFinal);
						}
						else{
							texto.append("Não foi possível renomear o arquivo "+nomeAntigo);
							if(endSucess){
								endSucess=!endSucess;
							}
						}
					}
					else{
						texto.append(nomeAntigo+" não possui o conteúdo desejado.");
					}
					texto.append("\n");
				}
				texto.append("\n");
				if(endSucess){
					texto.append("Todos os arquivos foram renomeados com sucesso.");
				}
				else{
					texto.append("Um ou mais arquivos não pode ser renomeado.");
				}
				texto.append("\n");
				Message.mensagemLimpa("Verifique o relatório do trabalho", TITULO);
			}
			catch(NullPointerException error){
				Message.mensagemErro("Arquivo não é legível", TITULO);
			}
		}
		if(e.getSource()==configuracoes && configuracoes.isEnabled()){//Se o usuário deseja alterar as configurações de divisão do PDF
			if(this.janelaConfiguracoes==null){//Se não houver janela criada
				this.criaJanelaConfiguracoes();
			}
			else{
				this.janelaConfiguracoes.setVisible(true);//Se já houver janela criada
			}
		}
		if((e.getSource()==listarArquivos && listarArquivos.isEnabled()) || e.getSource()==labelQtdeArquivos){//Se o usuário deseja ver quais são os arquivos que estão abertos
			texto.setText("");
			if(listaArquivosPDF.size()>0){
				StringBuilder temp=new StringBuilder();
				for(ArquivoPDF arquivoPDF:listaArquivosPDF){
					temp.append(arquivoPDF.getArquivo().getAbsolutePath());
					temp.append("\n");
				}
				texto.setText("Arquivos abertos:\n\n"+temp.toString());
			}
			else{
				Message.mensagemAlerta("Nenhum arquivo aberto no momento", TITULO);
			}
		}
		if(e.getSource()==checarConteudo && checarConteudo.isEnabled()){//Se o usuário deseja ver primeiro o conteúdo extraído antes de renomear
			if(this.listaArquivosPDF.size()>0){//Se houver arquivos abertos
				try{
					checarConteudo();
					Message.mensagemInformacao("Verifique os dados extraídos", TITULO);
				}
				catch(NullPointerException error){
					Message.mensagemErro("Arquivo não é legível", TITULO);
				}
			}
			else{
				Message.mensagemAlerta("Nenhum arquivo aberto no momento", TITULO);
			}
		}
		if(e.getSource()==sobre){//Se o usuário clica em SOBRE
			texto.setText("");
			texto.append("Ao abrir mais de um arquivo PDF, o sistema checa automaticamente se os arquivos possuem o conteúdo necessário."
					+ "\n");
			Message.mensagemInformacao("Desenvolvimento: Bruno Dornellas\ndornellas@bb.com.br\nF1691834", TITULO);
		}
		if(e.getSource()==salvarConfiguracoes){//Se o usuário deseja salvar as configurações de divisão do PDF
			if(this.textoPaginaInicial.getText().equals("") || this.textoPaginasPorArquivo.getText().equals("")){
				Message.mensagemAlerta("Preencha os campos", TITULO);
			}
			else{
				try{
					this.paginaInicial=Integer.parseInt(this.textoPaginaInicial.getText());
					this.paginasPorArquivo=Integer.parseInt(this.textoPaginasPorArquivo.getText());
					janelaConfiguracoes.setVisible(false);
					Message.mensagemInformacao("Dados salvos", TITULO);
				}
				catch(NumberFormatException exception){
					Message.mensagemErro("Informe valores válidos", TITULO);
				}
				if(this.textoPaginaFinal.getText().equals("")){
					this.paginaFinal=null;
				}
				else{
					try{
						this.paginaFinal=Integer.parseInt(this.textoPaginaFinal.getText());
					}
					catch(NumberFormatException exception){
						this.paginaFinal=null;
					}
				}
			}
		}
		if(e.getSource()==cancelarConfiguracoes){//Se o usuário deseja cancelar as alterações de configurações de divisão de PDF
			janelaConfiguracoes.setVisible(false);//Esconde a janela
		}
	}
}
