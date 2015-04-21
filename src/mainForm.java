import java.awt.EventQueue;
import java.awt.TextField;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import java.io.IOException;
import javax.swing.JProgressBar;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

class goLoad extends Thread
{
	static JTextField x2;
	static JProgressBar x3;
	static String station;
	@Override
    public void run()
    {
		String pt = null;
			try {
				pt = loadMacRadio.getDir();
				System.out.println(pt);
			} catch (IOException e) {
				e.printStackTrace();
			}
		try {
			loadMacRadio.x1 = x2;
			loadMacRadio.pb1=x3;
			loadMacRadio.loadMac(pt+"\\",station);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}//class goLoad extends Thread

public class mainForm {
	private JFrame frame;
	static goLoad mAnotherOpinion;
	private static JTextField tf1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainForm window = new mainForm();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public mainForm() {
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Хелло ворд");
		frame.setBounds(100, 100, 546, 197);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 520, 24);
		frame.getContentPane().add(panel);
		
		JLabel label = new JLabel("Скачать эфир Мак радио");
		label.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel.add(label);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 126, 520, 33);
		frame.getContentPane().add(panel_1);
		
		final JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"1-\u0422\u043E\u043F \u0445\u0438\u0442 (\u0438\u043C\u043F\u043E\u0440\u0442)", "2-\u0420\u0443\u0441\u0441\u043A\u0438\u0439 \u0445\u0438\u0442", "3-\u0414\u0435\u0442\u0441\u043A\u0438\u0439 \u0445\u0438\u0442", "4-\u0420\u0435\u0442\u0440\u043E \u0445\u0438\u0442", "5-\u0420\u0430\u0434\u0438\u043E\u043A\u0430\u0444\u0435", "6-\u0444\u0440\u0435\u0448"}));
		comboBox.setMaximumRowCount(6);
		comboBox.setBounds(131, 66, 203, 20);
		frame.getContentPane().add(comboBox);
		//final String[] radioStation  = comboBox.getSelectedItem().toString().split("-");
		
		final JProgressBar progressBar = new JProgressBar();
		progressBar.setForeground(Color.LIGHT_GRAY);
		progressBar.setBackground(Color.BLUE);
		progressBar.setBounds(10, 104, 510, 14);
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);
		frame.getContentPane().add(progressBar);
		final JButton btnNewButton = new JButton("Скачать");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
				btnNewButton.setEnabled(false);	
				SwingUtilities.invokeLater(new Runnable() {
				    public void run() {
				    	//***тут надо стартовать поток обновления****
						mAnotherOpinion = new goLoad();
						mAnotherOpinion.x2 = tf1;
						mAnotherOpinion.x3 = progressBar;
						mAnotherOpinion.station =comboBox.getSelectedItem().toString().split("-")[0]; 
						mAnotherOpinion.start();
				    }
				});
				}
				catch (Exception e){ 
				System.out.println("проблема");
				btnNewButton.setEnabled(true);
				progressBar.setValue(0);
			    }
			}
		});
		panel_1.add(btnNewButton);
		JButton btnNewButton_1 = new JButton("Выйти");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mAnotherOpinion.stop();
				tf1.setText("Жду...");
				progressBar.setValue(0);
				btnNewButton.setEnabled(true);
			}
		});
		panel_1.add(btnNewButton_1);
		tf1 = new JTextField();
		tf1.setBounds(10, 35, 510, 20);
		frame.getContentPane().add(tf1);
		tf1.setColumns(10);
		tf1.setText("Жду...");
		
		
		
		JLabel label_1 = new JLabel("\u0447\u0442\u043E \u043A\u0430\u0447\u0430\u0435\u043C ?");
		label_1.setBounds(20, 66, 101, 14);
		frame.getContentPane().add(label_1);
	}//private void initialize() 
}

