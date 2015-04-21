import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class testLoad extends Thread
{
	String g = "";
	JLabel x1;
	int x = 0;
	@Override
    public void run()
    {
		//****тут что то понапишем
    	for (int i = 0 ; i<32768;i++){
    		x1.setText(String.valueOf(i));
    		try {
    			Thread.sleep(1000);
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
    	}//for
    }//run()
}//class testLoad extends Thread

public class testForm {

	private JFrame frame;
	static testLoad m;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					testForm window = new testForm();
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
	public testForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 164);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final JLabel lblWait = new JLabel("wait");
		lblWait.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblWait.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblWait, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		
		final JButton button = new JButton("+");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
				    public void run() {
				    	//***тут надо стартовать поток обновления****
				    	m = new testLoad();
				    	m.x1 = lblWait;
						m.start();
				    }
				});
				button.setEnabled(false);
			}
		});
		panel.add(button);
		JButton button_1 = new JButton("-");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblWait.setText(String.valueOf(m.x));
				m.stop();
				button.setEnabled(true);
			}
		});
		panel.add(button_1);
	}//private void initialize()
}//public class testForm
