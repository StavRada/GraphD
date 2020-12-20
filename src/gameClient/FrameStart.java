package gameClient;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class FrameStart extends JFrame{
	
	private JFrame f ;
	
	public FrameStart() {
		f = new JFrame();
		f.getContentPane().setLayout(new FlowLayout());
		JButton Button=new JButton("Start"); 
		JLabel id=new JLabel ("Id "); 
		JTextField textfield1 = new JTextField("",10);
		JLabel level=new JLabel ("Level "); 
		JTextField textfield2 = new JTextField("",10);
		f.getContentPane().add(id);
		f.getContentPane().add(textfield1);
		f.getContentPane().add(level);
		f.getContentPane().add(textfield2);
		f.getContentPane().add(Button);
		f.pack();
		f.setVisible(true);
		
		Button.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {

				Ex2 ex2=new Ex2();
				ex2.setLevel_Id(Integer.parseInt(textfield1.getText()),Integer.parseInt(textfield2.getText()));
				Thread client = new Thread(ex2);
				client.start(); 		
				f.dispose();
			}
		}); 
	}

}
