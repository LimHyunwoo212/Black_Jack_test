import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SinglePlay extends JFrame implements ActionListener {

	public MainPage mainpage;
	private CardDeck cd;

	public Card[] player = new Card[11];
	public Card[] dealer = new Card[11];
	public int playerCount = 0;
	public int dealercount = 0;

	public JButton hit = new JButton("Hit");
	public JButton stand = new JButton("Stand");
	public JButton doubledown = new JButton("Double Down");

	JPanel dealerboard = new JPanel();
	JPanel playerboard = new JPanel();

	JLabel[] dcard = new JLabel[11];
	JLabel[] pcard = new JLabel[11];

	Betting betting;

	public SinglePlay(MainPage _mainpage) throws IOException {

		mainpage = _mainpage;
		betting = new Betting(this);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Dealer Board
		dealerboard.setLayout(null);
		for (int i = 10; i >= 0; i--) {
			dcard[i] = new JLabel();
			dcard[i].setBounds(20 + i * 16, 20, 71, 96);
			dealerboard.add(dcard[i]);
		}

		// Player Board
		playerboard.setLayout(null);
		for (int i = 10; i >= 0; i--) {
			pcard[i] = new JLabel();
			pcard[i].setBounds(20 + i * 16, 20, 71, 96);
			playerboard.add(pcard[i]);
		}

		// Play Panel
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(2, 1));
		centerPanel.setBackground(new Color(80, 140, 200));
		centerPanel.add(dealerboard);
		centerPanel.add(playerboard);
		dealerboard.setBackground(new Color(80, 140, 200));
		playerboard.setBackground(new Color(80, 140, 200));

		// Control Panel
		JPanel southPanel = new JPanel(null);
		southPanel.setPreferredSize(new Dimension(0, 130));
		southPanel.add(hit);
		southPanel.add(stand);
		southPanel.add(doubledown);
		southPanel.setBackground(Color.white);

		hit.setBounds(150, 10, 130, 30);
		stand.setBounds(150, 50, 130, 30);
		doubledown.setBounds(150, 90, 130, 30);

		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		c.add(centerPanel, BorderLayout.CENTER);
		c.add(southPanel, BorderLayout.SOUTH);

		setSize(300, 450);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screen.width - this.getSize().width) / 2,
				(screen.height - this.getSize().height) / 2);
		setResizable(false);

		hit.addActionListener(this);
		stand.addActionListener(this);
		doubledown.addActionListener(this);

		setActive(false);

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == hit) {
			Hit();

		} else if (e.getSource() == stand) {
			Stand();

		} else if (e.getSource() == doubledown) {
			Doubledown();

		}

	}

	public void setActive(boolean active) {
		hit.setEnabled(active);
		stand.setEnabled(active);
		doubledown.setEnabled(active);
	}

	public void getCard(boolean player, Card c) {
		if (player) {
			this.player[playerCount] = c;
			pcard[playerCount].setIcon(new ImageIcon("cards/" + CardImage(c)
					+ ".gif"));
			playerCount++;
			System.out.println(this.player[playerCount - 1].getSuit() + "."
					+ this.player[playerCount - 1].getCount());

		} else {
			this.dealer[dealercount] = c;
			if (dealercount == 0) {
				dcard[dealercount].setIcon(new ImageIcon("cards/b2fv.gif"));
			} else {
				dcard[dealercount].setIcon(new ImageIcon("cards/"
						+ CardImage(c) + ".gif"));
			}
			dealercount++;
		}
	}

	public void Setting() {

		cd = new CardDeck();
		try {
			Thread.sleep(3000);
			getCard(true, cd.newCard());
			//Thread.sleep(3000);
			getCard(false, cd.newCard());
			//Thread.sleep(3000);
			getCard(true, cd.newCard());
			//Thread.sleep(3000);
			getCard(false, cd.newCard());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		PlayerJudgement();

	}

	public boolean Hit() {
		getCard(true, cd.newCard());
		doubledown.setEnabled(false);
		return PlayerJudgement();
	}

	public void Stand() {
		Dealerplay();
	}

	public void Doubledown() {
		if (!betting.bet(betting.bmoney))
			return;
		if(Hit())
			Stand();
	}

	public boolean PlayerJudgement() {
		int score = PGetscore();
		if (score > 21) {
			//System.out.println("lose");
			setActive(false);
			mainpage.resultpage.showResult(false);
			return false;
		} else if (score == 21) {
			//System.out.println("win");
			setActive(false);
			mainpage.resultpage.showResult(true);
			return false;
		}
		//System.out.println("플레이어 점수 : " + score);
		return true;
	}

	public void Dealerplay() {
		int score = 0;
		do {
			score = DGetscore();
			System.out.println("딜러 점수 : " + score);
			if (score > 21) {
				mainpage.resultpage.showResult(true);
				return;
			} else if (score == 21) {
				mainpage.resultpage.showResult(false);
				setActive(false);
				return;
			} else {
				if (score <= 16) {
					getCard(false, cd.newCard());
				} else {
					break;
				}
			}

		} while (true);
		if (PGetscore() == DGetscore()) {
			mainpage.resultpage.showResult();
			//System.out.println("draw");
			setActive(false);
		} else if (PGetscore() > DGetscore()) {
			mainpage.resultpage.showResult(true);
			//System.out.println("win");
			setActive(false);
		} else if (PGetscore() < DGetscore()) {
			mainpage.resultpage.showResult(false);
			//System.out.println("lose");
			setActive(false);
		}

	}

	public int PGetscore() {
		int score = 0;
		int ace = 0;

		for (int i = 0; i < playerCount; i++) {
			if (player[i].getCount() == 1) {
				ace++;
				score++;
			} else if (player[i].getCount() > 10) {
				score += 10;
			} else
				score += player[i].getCount();
		}
		for (int i = 0; i < ace; i++){
			if (score  <= 11){
				score += 10;
			} else
				break;
		}
		System.out.println("Score of Player : " + score);
		return score;
	}

	public int DGetscore() {
		int score = 0;
		int ace = 0;

		for (int i = 0; i < dealercount; i++) {
			if (dealer[i].getCount() == 1) {
				ace++;
			} else if (dealer[i].getCount() > 10) {
				score += 10;
			} else
				score += dealer[i].getCount();
		}

		score += ace;

		if (ace > 0) {
			if (score <= 11) {
				return score + 10;
			} else {
				return score;
			}
		} else {
			return score;
		}
	}

	public String CardImage(Card c) {

		String suit = "";
		switch (c.getSuit()) {
			case "spades":
				suit = "s";
				break;
			case "diamonds":
				suit = "d";
				break;
			case "hearts":
				suit = "h";
				break;
			case "clubs":
				suit = "c";
				break;
		}
		return suit + c.getCount();
	}

}
