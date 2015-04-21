import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class goLoad1 extends Thread
{
	static JLabel l1;
	static JLabel l2;
	static JLabel l3;
	String u;
	
	@Override
    public void run()
    {
		try {
			//***тут передаем параметру в класс скачки
			
			parselbum.path = loadMacRadio.getDir()+"\\";
			parselbum.userId = u;
			parselbum.l11 =l1;
			parselbum.l12 =l2;
			parselbum.l13 =l3;
			parselbum.goParse();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}//class goLoad extends Thread

class goMultiLoad extends Thread
{
	//***********************************
	//*поток для пакетной загрузки
	//***********************************
	static JLabel l1;
	static JLabel l2;
	static JLabel l3;
	String u;
	
	@Override
    public void run()
    {
		try {
			//***тут передаем параметру в класс скачки
			
			parselbum.path = loadMacRadio.getDir()+"\\";
			//*****считаем и ТХТ файла показатели и последовательно переберем их*******
			String[] user =loadData("get.txt");
			for (int i = 0; i < user.length; i++) {
			parselbum.userId = user[i];
			parselbum.l11 =l1;
			parselbum.l12 =l2;
			parselbum.l13 =l3;
			parselbum.goParse();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	static String[] loadData(String lnk) throws Exception{
		//*******************************************************
		//*универсальная надстрока, вернет String[] ответ по lnk
		//*читаем тхт файл
		//*******************************************************
		
			String[] everything=null;
			BufferedReader br = new BufferedReader(new FileReader(lnk));
		    try {
		        StringBuilder sb = new StringBuilder();
		        String line = br.readLine();

		        while (line != null) {
		            sb.append(line);
		            sb.append("\n");
		            line = br.readLine();
		        }
		        everything = sb.toString().split("\\s+");
		    } finally {
		        br.close();
		    }
		    return everything;
		    
		} //static String[] loadData(String lnk) throws Exception
		
}//class goMultiLoad extends Thread

public class yaForm {

	private JFrame frmLoadYandexFoto;
	private JTextField userId;
	goLoad1 gl;
	goMultiLoad gl1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					yaForm window = new yaForm();
					window.frmLoadYandexFoto.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public yaForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLoadYandexFoto = new JFrame();
		frmLoadYandexFoto.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 229, 434, 33);
		frmLoadYandexFoto.getContentPane().add(panel);
		
		userId = new JTextField();
		userId.setBounds(76, 8, 225, 20);
		frmLoadYandexFoto.getContentPane().add(userId);
		userId.setColumns(10);
		
		final JLabel curAlb = new JLabel("alb:");
		curAlb.setFont(new Font("Tahoma", Font.BOLD, 14));
		curAlb.setBounds(10, 83, 291, 14);
		frmLoadYandexFoto.getContentPane().add(curAlb);
		
		final JLabel curPh = new JLabel("photos:");
		curPh.setFont(new Font("Tahoma", Font.BOLD, 13));
		curPh.setBounds(10, 108, 291, 14);
		frmLoadYandexFoto.getContentPane().add(curPh);
		
		final JLabel curUserId = new JLabel("User ID");
		curUserId.setBounds(10, 11, 56, 14);
		frmLoadYandexFoto.getContentPane().add(curUserId);
		
		final JLabel curUsr = new JLabel("ID:");
		curUsr.setFont(new Font("Tahoma", Font.BOLD, 14));
		curUsr.setBounds(10, 58, 291, 14);
		frmLoadYandexFoto.getContentPane().add(curUsr);
		
		final JButton btnNewButton = new JButton("Загрузить пакетом");
		final JButton btnLoad = new JButton("Загрузить по ID");
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnLoad.setEnabled(false);	
				btnNewButton.setEnabled(false);
				SwingUtilities.invokeLater(new Runnable() {
				    public void run() {
				    	//***тут надо стартовать поток обновления****
						gl1 = new goMultiLoad();
						gl1.u =userId.getText();
						gl1.l1 = curAlb;
						gl1.l2 = curPh;
						gl1.l3 = curUsr;
						gl1.start();
				    }
				});
			}
		});
		panel.add(btnNewButton);
		
		
		
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnLoad.setEnabled(false);	
				btnNewButton.setEnabled(false);
				SwingUtilities.invokeLater(new Runnable() {
				    public void run() {
				    	//***тут надо стартовать поток обновления****
						gl = new goLoad1();
						gl.u =userId.getText();
						gl.l1 = curAlb;
						gl.l2 = curPh;
						gl.l3 = curUsr;
						gl.start();
				    }
				});
			}
		});
		panel.add(btnLoad);
		
		JButton btnStop = new JButton("Остановить");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnLoad.setEnabled(true);
				btnNewButton.setEnabled(true);
				try {gl.stop();}
				catch (Exception e1) {};
				try {gl1.stop();}
				catch (Exception e1) {};
			}
		});
		
		
		panel.add(btnStop);
		frmLoadYandexFoto.setTitle("Загрузка фотографий");
		frmLoadYandexFoto.setBounds(100, 100, 450, 299);
		frmLoadYandexFoto.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
