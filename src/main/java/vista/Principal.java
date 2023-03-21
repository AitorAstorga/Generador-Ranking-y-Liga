package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;

import clasificaciones.Clasificacion;
import excel.EscritorExcel;
import modelo.Deportista;
import modelo.ModeloColumnasTablaRanking;
import modelo.ModeloTabla;
import modelo.Temporada;
import vista.dialogos.DialogoAñadirDeportista;
import vista.dialogos.DialogoAñadirManual;
import vista.dialogos.DialogoAñadirTotales;
import vista.dialogos.DialogoModificarDeportista;

public class Principal extends JFrame {
	final static String LOGO_FAE = "iconos/FAE logo nuevo MEM.jpg";
	final static String TITULO = "Generador Ranking y Liga";
	final static int DEFAULT_BORDER = 10;
	final static int LOGO_SIZE = 100;
	final static int WINDOW_WIDTH = 1000;
	final static int WINDOW_HEIGTH = 600;
	
	final static String ACC_AÑADIR_DEPORTISTA = "Añadir Deportista";
	final static String ACC_RESULTADOS_TOTALES = "Añadir Resultados Totales";
	final static String ACC_RESULTADO_MANUAL = "Añadir Resultado Manual";
	final static String ACC_MODIFICAR_DEPORTISTA = "Modificar deportista";
	final static String ACC_GENERAR_EXCEL = "Generar Excel";
	
	final static String ACC_R_ABS_M = "Ranking ABS M";
	final static String ACC_R_ABS_F = "Ranking ABS F";
	final static String ACC_R_JUN_M = "Ranking JUN M";
	final static String ACC_R_JUN_F = "Ranking JUN F";
	final static String ACC_LIGA_M = "Liga M";
	final static String ACC_LIGA_F = "Liga F";
	
	final static String ACC_SALIR = "Salir";
	
	AccionBoton accAñadirTirador, accAñadirTotales, accAñadirManual, accModificarCompeticion, accExcel;
	AccionBoton accR_ABS_M, accR_ABS_F, accR_JUN_M, accR_JUN_F, accLIGA_M, accLIGA_F, accSalir;
	
	public static Clasificacion clasificacionSeleccionada = Clasificacion.R_ABS_M;

	ModeloTabla modelo;
	Temporada temporada;
	JTable tabla;
	ModeloColumnasTablaRanking columnas;
	RendererRankingABS renderer;
	
	public Principal() {
		super(TITULO);
		
		cargarTemporada();
		if(temporada == null) {
			temporada = new Temporada(2021);
		}
		modelo = new ModeloTabla(temporada);
		renderer = new RendererRankingABS();
		columnas = new ModeloColumnasTablaRanking(renderer, temporada);
		
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGTH);
		centrarVentana();
		crearAcciones();
		this.setContentPane(crearPanelVentana());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent e) {
		    	guardarTemporada();
		    }
		});
		this.setVisible(true);
	}

	private void centrarVentana() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
		this.setLocation(x, y);
	}
	
	public void guardarTemporada() {
		try {
            FileOutputStream fileOut = new FileOutputStream(Temporada.FICHERO_TEMPORADA);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(temporada);
            objectOut.close(); 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
	public void cargarTemporada() {
		try {
			FileInputStream fileIn = new FileInputStream(Temporada.FICHERO_TEMPORADA);
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			temporada = (Temporada) objectIn.readObject();
			objectIn.close();
        } catch (Exception ex) { ex.printStackTrace(); }
	}
	
	private void crearAcciones() {
		accAñadirTirador = new AccionBoton(ACC_AÑADIR_DEPORTISTA);
		accAñadirTotales = new AccionBoton(ACC_RESULTADOS_TOTALES);
		accAñadirManual = new AccionBoton(ACC_RESULTADO_MANUAL);
		accModificarCompeticion = new AccionBoton(ACC_MODIFICAR_DEPORTISTA);
		
		accR_ABS_M = new AccionBoton(ACC_R_ABS_M);
		accR_ABS_F = new AccionBoton(ACC_R_ABS_F);
		accR_JUN_M = new AccionBoton(ACC_R_JUN_M);
		accR_JUN_F = new AccionBoton(ACC_R_JUN_F);
		accLIGA_M = new AccionBoton(ACC_LIGA_M);
		accLIGA_F = new AccionBoton(ACC_LIGA_F);
		
		accExcel = new AccionBoton(ACC_GENERAR_EXCEL);
	}
	
	private class AccionBoton extends AbstractAction {
		String texto;
		
		public AccionBoton (String texto){
			super(texto);
			this.texto = texto;
		}
		
		private void reiniciarTabla() {
			columnas = new ModeloColumnasTablaRanking(renderer, temporada);
			tabla.setColumnModel(columnas);
			tabla.repaint();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (texto) {
				case ACC_AÑADIR_DEPORTISTA:
					try { 
						DialogoAñadirDeportista dialog = new DialogoAñadirDeportista(modelo);
					} catch (Exception e1) { e1.printStackTrace(); }
					break;
				case ACC_RESULTADOS_TOTALES:
					try {
						DialogoAñadirTotales dialog = new DialogoAñadirTotales(modelo);
					} catch (Exception e1) { e1.printStackTrace(); }
					break;
				case ACC_RESULTADO_MANUAL:
					try {
						DialogoAñadirManual dialog = new DialogoAñadirManual(modelo);
					} catch (Exception e1) { e1.printStackTrace(); }
					break;
				case ACC_MODIFICAR_DEPORTISTA:
					try {
						DialogoModificarDeportista dialog = new DialogoModificarDeportista(modelo);
					} catch (Exception e1) { e1.printStackTrace(); }
					break;
				case ACC_GENERAR_EXCEL:
					System.out.println(ACC_GENERAR_EXCEL);
					try {
						EscritorExcel.writeToExcell(tabla, Temporada.FICHERO_TEMPORADA);
					} catch (IOException e1) { e1.printStackTrace(); }
					break;
				case ACC_R_ABS_M:
					clasificacionSeleccionada = Clasificacion.R_ABS_M;
					reiniciarTabla();
					break;
				case ACC_R_ABS_F:
					clasificacionSeleccionada = Clasificacion.R_ABS_F;
					reiniciarTabla();
					break;
				case ACC_R_JUN_M:
					System.out.println(ACC_R_JUN_M);
					clasificacionSeleccionada = Clasificacion.R_JUN_M;
					reiniciarTabla();
					break;
				case ACC_R_JUN_F:
					clasificacionSeleccionada = Clasificacion.R_JUN_F;
					reiniciarTabla();
					break;
				case ACC_LIGA_M:
					clasificacionSeleccionada = Clasificacion.LIGA_M;
					reiniciarTabla();
					break;
				case ACC_LIGA_F:
					clasificacionSeleccionada = Clasificacion.LIGA_F;
					reiniciarTabla();
					break;
			}
		}
	}

	private Container crearPanelVentana() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(crearPanelSuperior(), BorderLayout.NORTH);
		panel.add(crearPanelCentral(), BorderLayout.CENTER);
		return panel;
	}

	private ImageIcon crearLogoAjustado(int altura) {
		ImageIcon imageIcon = new ImageIcon(new ImageIcon(LOGO_FAE).getImage().getScaledInstance(LOGO_SIZE,
				LOGO_SIZE * 317 / 450, Image.SCALE_DEFAULT));
		return imageIcon;
	}

	private Component crearPanelSuperior() {
		JPanel panel = new JPanel(new BorderLayout(DEFAULT_BORDER, DEFAULT_BORDER));
		panel.setBackground(Color.WHITE);
		panel.setBorder(
				BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(Color.BLACK),
						BorderFactory.createEmptyBorder(DEFAULT_BORDER, DEFAULT_BORDER, DEFAULT_BORDER, DEFAULT_BORDER)));
		JLabel logo = new JLabel(crearLogoAjustado(LOGO_SIZE));
		JLabel titulo = new JLabel(TITULO, JLabel.CENTER);
		titulo.setFont(new Font("Arial", Font.BOLD, 32));

		panel.add(logo, BorderLayout.WEST);
		panel.add(titulo, BorderLayout.CENTER);

		return panel;
	}

	private Component crearPanelCentral() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		panel.setBackground(Color.GREEN);
		panel.add(crearPanelLigasRankings(), BorderLayout.NORTH);
		panel.add(crearPanelJTable(), BorderLayout.CENTER);
		panel.add(crearPanelDerecho(), BorderLayout.EAST);
		return panel;
	}

	private Component crearPanelLigasRankings() {
		JPanel panel = new JPanel(new GridLayout(1, 6, 0, 0));
		panel.setBorder(
				BorderFactory.createCompoundBorder(
						BorderFactory.createEmptyBorder(DEFAULT_BORDER, DEFAULT_BORDER, 0, DEFAULT_BORDER),
						BorderFactory.createCompoundBorder(
							BorderFactory.createLineBorder(Color.BLACK),
							BorderFactory.createEmptyBorder(DEFAULT_BORDER, DEFAULT_BORDER, DEFAULT_BORDER, DEFAULT_BORDER))));
	
		JButton btRankingAbsM = new BotonPersonalizado(accR_ABS_M);
		JButton btRankingAbsF = new BotonPersonalizado(accR_ABS_F);
		JButton btRankingJunM = new BotonPersonalizado(accR_JUN_M);
		JButton btRankingJunF = new BotonPersonalizado(accR_JUN_F);
		JButton btLigaM = new BotonPersonalizado(accLIGA_M);
		JButton btLigaF = new BotonPersonalizado(accLIGA_F);

		panel.add(btRankingAbsM);
		panel.add(btRankingAbsF);
		panel.add(btRankingJunM);
		panel.add(btRankingJunF);
		panel.add(btLigaM);
		panel.add(btLigaF);
		return panel;
	}

	private Component crearPanelJTable() {
		tabla = new JTable(modelo, columnas);
		JScrollPane panelTabla = new JScrollPane(tabla, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panelTabla.setBorder(
				BorderFactory.createCompoundBorder(
					BorderFactory.createEmptyBorder(DEFAULT_BORDER, DEFAULT_BORDER, DEFAULT_BORDER, DEFAULT_BORDER),
					BorderFactory.createLineBorder(Color.BLACK)));
		tabla.setDefaultRenderer(Deportista.class, new RendererRankingABS());
		tabla.getColumnModel().getColumn(0).setCellRenderer (new RendererRankingABS());
		panelTabla.setViewportView(tabla);
		return panelTabla;
	}

	private Component crearPanelDerecho() {
		JPanel panel = new JPanel(new GridLayout(2,1));
		panel.setBorder(BorderFactory.createEmptyBorder(DEFAULT_BORDER, DEFAULT_BORDER, DEFAULT_BORDER, DEFAULT_BORDER));
		panel.add(crearPanelOpciones());
		panel.add(crearPanelTemporadas());
		return panel;
	}

	private Component crearPanelOpciones() {
		JPanel panel = new JPanel(new GridLayout(5, 1, DEFAULT_BORDER, DEFAULT_BORDER));
		panel.setBorder(
				BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(Color.BLACK),
					BorderFactory.createEmptyBorder(DEFAULT_BORDER, DEFAULT_BORDER, DEFAULT_BORDER, DEFAULT_BORDER)));
		JButton btAñadirTirador = new BotonPersonalizado(accAñadirTirador);
		JButton btResultadosTotales = new BotonPersonalizado(accAñadirTotales);
		JButton btResultadoManual = new BotonPersonalizado(accAñadirManual);
		JButton btModificarCompeticion = new BotonPersonalizado(accModificarCompeticion);
		JButton btGenerarExcel = new BotonPersonalizado(accExcel);
		
		panel.add(btAñadirTirador);
		panel.add(btResultadosTotales);
		panel.add(btResultadoManual);
		panel.add(btModificarCompeticion);
		panel.add(btGenerarExcel);
		return panel;
	}

	private Component crearPanelTemporadas() {
		JPanel panel = new JPanel(new GridLayout(4, 1, DEFAULT_BORDER, DEFAULT_BORDER));
		panel.setBorder(
				BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(Color.BLACK),
					BorderFactory.createEmptyBorder(DEFAULT_BORDER, DEFAULT_BORDER, DEFAULT_BORDER, DEFAULT_BORDER)));
	
		JButton btTemporada21 = new BotonPersonalizado("Temporada 2021-22", "https://alavaesgrima.files.wordpress.com/2022/01/ranking-de-alava-2021-22-abs.pdf");
		JButton btTemporada20 = new BotonPersonalizado("Temporada 2020-21", "https://alavaesgrima.files.wordpress.com/2021/06/ranking-de-alava-2020-21.pdf");
		JButton btTemporada19 = new BotonPersonalizado("Temporada 2019-20", "https://alavaesgrima.files.wordpress.com/2021/01/ranking-de-alava-2019-20.pdf");
		JButton btTemporada18 = new BotonPersonalizado("Temporada 2018-19", "https://alavaesgrima.files.wordpress.com/2019/08/ranking-de-acc81lava-2018-19.pdf");
		panel.add(btTemporada21);
		panel.add(btTemporada20);
		panel.add(btTemporada19);
		panel.add(btTemporada18);
		return panel;
	}

	public static void main(String[] args) {
		Principal principal = new Principal();
		try{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) { e.printStackTrace(); }
	}

}
