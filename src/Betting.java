import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Betting extends JFrame implements ActionListener {

	public int money = 0;
	public int bmoney = 0;
	public JLabel label = new JLabel();
	JButton chip1 = new JButton("$1");
	JButton chip5 = new JButton("$5");
	JButton chip10 = new JButton("$10");
	JButton chip50 = new JButton("$50");
	SinglePlay singleplay;

	public Betting(SinglePlay _singlepage) throws IOException {

		gamedata();

		singleplay = _singlepage;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		label.setText("잔액 : " + money);

		Container c = getContentPane();
		c.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
		c.add(label);
		c.add(chip1);
		c.add(chip5);
		c.add(chip10);
		c.add(chip50);
		c.setBackground(Color.white);
		setSize(50, 230);

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screen.width - 300) / 2 + 320,
				(screen.height - 450) / 2 + 210);
		setResizable(false);

		chip1.addActionListener(this);
		chip5.addActionListener(this);
		chip10.addActionListener(this);
		chip50.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {

		int bm = 0;
		if (e.getSource() == chip1) {
			if (money < 1)
				return;
			bm = 1;
		} else if (e.getSource() == chip5) {
			if (money < 5)
				return;
			bm = 5;
		} else if (e.getSource() == chip10) {
			if (money < 10)
				return;
			bm = 10;
		} else if (e.getSource() == chip50) {
			if (money < 50)
				return;
			bm = 50;
		}

		if (bet(bm) == true) {
			setVisible(false);
			singleplay.setActive(true);
		}
		label.setText("잔액 : " + money);
		singleplay.Setting();

	}

	public boolean bet(int bet) {

		if (bet <= this.money) {
			this.money -= bet;
			this.bmoney += bet;
			// System.out.println(this.money);
			return true;
		} else {
			return false;
		}
	}

	public void gamedata() throws IOException {
		BufferedReader infile = new BufferedReader(new FileReader("gamedata.txt"));
		money = new Integer(infile.readLine()).intValue();
		infile.close();
	}
}
