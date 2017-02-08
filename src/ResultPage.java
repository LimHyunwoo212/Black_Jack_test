import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ResultPage extends JFrame implements ActionListener {

	SinglePlay singleplay;

	JButton continuebutton = new JButton("예");
	JButton exitbutton = new JButton("아니요");

	public ResultPage(SinglePlay _singleplay) {

		singleplay = _singleplay;

		JPanel panel = new JPanel(new FlowLayout());
		JLabel label = new JLabel("계속하시겠습니까?", JLabel.CENTER);
		this.setLayout(new BorderLayout());
		this.add(label);
		this.add(panel, BorderLayout.SOUTH);
		panel.add(continuebutton);
		panel.add(exitbutton);
		this.setPreferredSize(new Dimension(0, 130));
		setSize(300, 150);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screen.width - this.getSize().width) / 2 + 320,
				(screen.height - this.getSize().height) / 2 + 100);
		setResizable(false);

		continuebutton.addActionListener(this);
		exitbutton.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == continuebutton) {
			this.setVisible(false);
			for (int i = 0; i < 11; i++) {
				singleplay.dcard[i].setIcon(null);
				singleplay.pcard[i].setIcon(null);
			}
			singleplay.playerCount = 0;
			singleplay.dealercount = 0;
			singleplay.setVisible(true);
			singleplay.betting.setVisible(true);
			System.out.println("또 할래");
		} else if (e.getSource() == exitbutton) {
			this.setVisible(false);
			for (int i = 0; i < 11; i++) {
				singleplay.dcard[i].setIcon(null);
				singleplay.pcard[i].setIcon(null);
			}
			singleplay.playerCount = 0;
			singleplay.dealercount = 0;
			singleplay.setVisible(false);
			singleplay.mainpage.setVisible(true);
		}
	}

	public void showResult(boolean playerwin) {
		singleplay.dcard[0].setIcon(new ImageIcon(("cards/"
				+ singleplay.CardImage(singleplay.dealer[0]) + ".gif")));
		if (playerwin) {
			setTitle("승리");
			singleplay.betting.money += 2 * singleplay.betting.bmoney;
			singleplay.betting.label
					.setText("잔액 : " + singleplay.betting.money);
			try {
				PrintWriter outfile = new PrintWriter(new FileWriter(
						"gamedata.txt"));
				outfile.print(singleplay.betting.money);
				outfile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			setTitle("패배");
			singleplay.betting.label
					.setText("잔액 : " + singleplay.betting.money);
			try {
				PrintWriter outfile = new PrintWriter(new FileWriter(
						"gamedata.txt"));
				outfile.print(singleplay.betting.money);
				outfile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		singleplay.betting.bmoney = 0;
		setVisible(true);
	}

	public void showResult() {
		singleplay.dcard[0].setIcon(new ImageIcon(("cards/"
				+ singleplay.CardImage(singleplay.dealer[0]) + ".gif")));
		setTitle("무승부");
		singleplay.betting.money += singleplay.betting.bmoney;
		singleplay.betting.label.setText("잔액 : " + singleplay.betting.money);
		singleplay.betting.bmoney = 0;
		setVisible(true);
	}
}
