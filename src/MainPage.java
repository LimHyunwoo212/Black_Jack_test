import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainPage extends JFrame implements ActionListener {

	// SinglePlay
	SinglePlay singleplay;

	// ResultPage
	ResultPage resultpage;

	// JLabel
	JLabel title = new JLabel(new ImageIcon("bj.jpg"));
	JLabel version = new JLabel("v 1.0 ", JLabel.RIGHT);

	// JButton
	JButton single = new JButton("혼자서");
	JButton multi = new JButton("여럿이");
	JButton option = new JButton("옵션");
	JButton exit = new JButton("끝내기");

	public MainPage() throws IOException {

		singleplay = new SinglePlay(this);
		resultpage = new ResultPage(singleplay);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		single.setBounds(100, 0, 100, 25);
		multi.setBounds(100, 30, 100, 25);
		option.setBounds(100, 60, 100, 25);
		exit.setBounds(100, 90, 100, 25);

		multi.setEnabled(false);
		option.setEnabled(false);

		JPanel p1 = new JPanel(null);
		p1.setPreferredSize(new Dimension(0, 130));
		p1.add(single);
		p1.add(multi);
		p1.add(option);
		p1.add(exit);
		p1.setBackground(Color.white);

		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		c.setBackground(Color.white);
		c.add(version, BorderLayout.NORTH);
		c.add(title, BorderLayout.CENTER);
		c.add(p1, BorderLayout.SOUTH);

		setSize(300, 450);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screen.width - this.getSize().width) / 2,
				(screen.height - this.getSize().height) / 2);
		setVisible(true);
		setResizable(false);

		single.addActionListener(this);
		multi.addActionListener(this);
		option.addActionListener(this);
		exit.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == single) {
			this.setVisible(false);
			singleplay.setVisible(true);
			singleplay.betting.setVisible(true);
			// System.out.println("혼자서 할래");
		} else if (e.getSource() == multi) {
			// System.out.println("여럿이 할래");
		} else if (e.getSource() == option) {
			// System.out.println("설정 할래");
		} else if (e.getSource() == exit) {
			System.exit(0);
		}
	}

	public static void main(String[] args) throws IOException {
		new MainPage();
	}

}