package View;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.junit.Test;
import Model.Card;
import Model.InitializePhase;
import Model.Player;

/**
 * <h1>CardView</h1> 
 * This class implements card view.
 *
 * @author chenwei_song,youlin_liu,shuo_chi
 * @version 3.0
 * @since 2019-03-14
 */
public class CardView extends Observable implements Observer {
	public HashMap<String, Player> playerSet = new HashMap<>();
	public HashMap<String, String> boxes = new HashMap<>();
	public LinkedList<Card> cards = new LinkedList<>();
	private InitializePhase model;
	public String player;
	private JButton OK;

	JFrame frame = new JFrame("Card View");

	/**
	 * It is observer override update method update players' card information.
	 *
	 * @param obs The observable object.
	 * @param x   An argument passed to the notifyObservers method.
	 */
	@Override
	public void update(Observable obs, Object x) {
		playerSet = ((InitializePhase) obs).getPlayerSet();
	}

	/**
	 * It is constructor of card view to upload a thread of JFrame show all
	 * buttons,labels, texts.
	 * 
	 * @param model It is initializePhase class object.
	 */
	public CardView(InitializePhase model) {

		this.model = model;

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {

				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException ex) {
					ex.printStackTrace();
				}

				frame.setUndecorated(true);
				if (cards.size() < 3) {
					try {
						frame.add(new showallCard());
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else if (cards.size() >= 3 && cards.size() < 5) {
					frame.add(new CardPane());
				}

				else if (cards.size() >= 5) {
					frame.add(new mustChange());
				}
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);

			}
		});

	}

	/**
	 * It is a method receiving player card information.
	 *
	 * @param p        Player name.
	 * @param cardlist A linked list stores all card information.
	 */
	public void receive(String p, LinkedList<Card> cardlist) {
		cards = cardlist;
		player = p;

	}

	/**
	 * It is a method calculate card size to decide which label can click.
	 */
	public void calculate() {
		LinkedList<String> selectCard = new LinkedList<>();
		if (boxes.size() == 3) {
			for (String key : boxes.keySet()) {
				selectCard.add(boxes.get(key));
			}
			if (isSame(selectCard) || isDifferent(selectCard)) {
				OK.setEnabled(true);
			} else {
				OK.setEnabled(false);
			}
		} else {
			OK.setEnabled(false);
		}
	}

	/**
	 * It is a method check whether selected card is same or not.
	 *
	 * @param selectCard A linked list stores which cards player chooses.
	 * @return true if the card selected are same, otherwise false.
	 */
	public Boolean isSame(LinkedList<String> selectCard) {
		if (selectCard.get(0).equals(selectCard.get(1)) && selectCard.get(0).equals(selectCard.get(2)))
			return true;
		return false;
	}

	/**
	 * It is a method check whether selected cards is different or not.
	 *
	 * @param selectCard A linked list stores which cards player chooses.
	 * @return true if the card selected aren't same, otherwise false.
	 */
	public Boolean isDifferent(LinkedList<String> selectCard) {
		if (!selectCard.get(0).equals(selectCard.get(1)) && !selectCard.get(0).equals(selectCard.get(2))
				&& !selectCard.get(1).equals(selectCard.get(2)))
			return true;
		return false;
	}

	/**
	 * It is a method delete card.
	 *
	 * @return A linked list stores card information which had been updated.
	 */
	 public LinkedList<Card> delete() {
   		 ArrayList<String> selectCard = new ArrayList<>();
   		 LinkedList<Card> list = new LinkedList<>();
   		 for (int i = 0; i < cards.size(); i++) {
			list.add(cards.get(i));
		}
   		 for(String key : boxes.keySet()){
					selectCard.add(boxes.get(key));
				}
   		 for (int i = 0; i < cards.size(); i++) {
   				if(selectCard.get(0).equals(cards.get(i).getName())) {
   					
   					list.get(i).setName(" ");;
   					break;
   				}
   			}
   		 for (int i = 0; i < cards.size(); i++) {
   				if(selectCard.get(1).equals(cards.get(i).getName())) {
   				
   					list.get(i).setName(" ");
   					break;
   				}
   			}
   		 for (int i = 0; i < cards.size(); i++) {
   				if(selectCard.get(2).equals(cards.get(i).getName())) {
   					list.get(i).setName(" ");
   					break;
   				}
   			}
   		 LinkedList<Card> target = new LinkedList<>();
   		 for (int i = 0; i < list.size(); i++) {
			if (!list.get(i).getName().equals(" ")) {
				target.add(list.get(i));
			}
		}
 
   		return target;
		}

	 /**
     * It is a JPanel to add buttons, labels, pictures. it can receive mouse.
     * listener component.
     */
	public class showallCard extends JPanel {
		
		/**
		 * This method shows all card to the users.
		 * 
		 * @throws IOException If would not read the file.
		 */
		public showallCard() throws IOException {
			File infantry = new File("resource/infantry.png");
			File cavalry = new File("resource/cavalry.png");
			File artillery = new File("resource/artillery.png");

			BufferedImage inf = ImageIO.read(infantry);
			BufferedImage cav = ImageIO.read(cavalry);
			BufferedImage art = ImageIO.read(artillery);
			for (int i = 0; i < cards.size(); i++) {
				String name = cards.get(i).getName();
				if (name.equals("i")) {
					JLabel label = new JLabel(new ImageIcon(inf));
					add(label);
				} else if (name.equals("c")) {
					JLabel label = new JLabel(new ImageIcon(cav));
					add(label);
				} else if (name.equals("a")) {
					JLabel label = new JLabel(new ImageIcon(art));
					add(label);
				}
			}

			JButton ok = new JButton("ok");
			add(ok);
			ok.addActionListener(new ActionListener() {
				
				/**
				 * This method will be invoked when an action occurs.
				 */
				@Override
				public void actionPerformed(ActionEvent e) {
					frame.dispose();
					model.cardArmy(player, cards, true);

				}
			});

		}

		/**
		 * It is an override method of JFrame it defines the size of window.
		 *
		 * @return The size of JFrame.
		 */
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(650, 300);
		}
	}

	/**
	 * It is a JPanel to add buttons, labels, pictures. it can receive mouse
	 * listener component.
	 */
	public class CardPane extends JPanel {
		checkCard cc = new checkCard();

		public CardPane() {

			try {

				File infantry = new File("resource/infantry.png");
				File cavalry = new File("resource/cavalry.png");
				File artillery = new File("resource/artillery.png");

				BufferedImage inf = ImageIO.read(infantry);
				BufferedImage cav = ImageIO.read(cavalry);
				BufferedImage art = ImageIO.read(artillery);
				for (int i = 0; i < cards.size(); i++) {
					String name = cards.get(i).getName();
					if (name.equals("i")) {
						JCheckBox jcb = new JCheckBox();
						jcb.setSelected(false);
						jcb.addMouseListener(cc);
						jcb.setName(String.valueOf(i));
						jcb.setText(name);
						add(jcb);
						JLabel label = new JLabel(new ImageIcon(inf));
						add(label);
					} else if (name.equals("c")) {
						JCheckBox jcb = new JCheckBox();
						jcb.setSelected(false);
						jcb.addMouseListener(cc);
						jcb.setName(String.valueOf(i));
						jcb.setText(name);
						add(jcb);
						JLabel label = new JLabel(new ImageIcon(cav));
						add(label);
					} else if (name.equals("a")) {
						JCheckBox jcb = new JCheckBox();
						jcb.setSelected(false);
						jcb.addMouseListener(cc);
						jcb.setName(String.valueOf(i));
						jcb.setText(name);
						add(jcb);
						JLabel label = new JLabel(new ImageIcon(art));
						add(label);
					}
				}

				OK = new JButton("OK");
				OK.setName("OK");
				OK.setEnabled(false);
				OK.addMouseListener(cc);
				add(OK);

				JButton cancel = new JButton("cancel");
				add(cancel);
				cancel.addActionListener(new ActionListener() {
					
					/**
					 * This method will be invoked when an action occurs.
					 */
					@Override
					public void actionPerformed(ActionEvent e) {
						model.cardArmy(player, cards, true);
						frame.dispose();
					}
				});

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		/**
		 * It is an override method of JFrame it defines the size of window.
		 *
		 * @return The size of JFrame.
		 */
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(650, 650);
		}

		/**
		 * This is a check card view.
		 */
		public class checkCard extends MouseAdapter {

			/**
			 * This method will be invoked when an action occurs.
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				
				String obtain = e.getComponent().getName();
				if (obtain.equals("OK")) {
					LinkedList<Card> cardlist = delete();
					model.cardArmy(player, cardlist, true);
					frame.dispose();
				} else {
					JCheckBox b = (JCheckBox) e.getComponent();
					if (b.isSelected()) {
						boxes.put(b.getName(), b.getText());
						calculate();

					} else {
						boxes.remove(b.getName());
						calculate();
					}

				}
			}

		}

	}

	/**
	 * It is an mouse adapter class to process the mouse operation we only use mouse
	 * click.
	 */
	public class mustChange extends JPanel {
		checkHandler ch = new checkHandler();

		/**
		 * This method implements updating card view.
		 */
		public mustChange() {

			try {

				File infantry = new File("resource/infantry.png");
				File cavalry = new File("resource/cavalry.png");
				File artillery = new File("resource/artillery.png");

				BufferedImage inf = ImageIO.read(infantry);
				BufferedImage cav = ImageIO.read(cavalry);
				BufferedImage art = ImageIO.read(artillery);
				for (int i = 0; i < cards.size(); i++) {
					String name = cards.get(i).getName();
					if (name.equals("i")) {
						JCheckBox jcb = new JCheckBox();
						jcb.setSelected(false);
						jcb.addMouseListener(ch);
						jcb.setName(String.valueOf(i));
						jcb.setText(name);
						add(jcb);
						JLabel label = new JLabel(new ImageIcon(inf));
						add(label);
					} else if (name.equals("c")) {
						JCheckBox jcb = new JCheckBox();
						jcb.setSelected(false);
						jcb.addMouseListener(ch);
						jcb.setName(String.valueOf(i));
						jcb.setText(name);
						add(jcb);
						JLabel label = new JLabel(new ImageIcon(cav));
						add(label);
					} else if (name.equals("a")) {
						JCheckBox jcb = new JCheckBox();
						jcb.setSelected(false);
						jcb.addMouseListener(ch);
						jcb.setName(String.valueOf(i));
						jcb.setText(name);
						add(jcb);
						JLabel label = new JLabel(new ImageIcon(art));
						add(label);
					}
				}

				OK = new JButton("OK");
				OK.setName("OK");
				OK.setEnabled(false);
				OK.addMouseListener(ch);
				add(OK);

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		/**
		 * It is an override method of JFrame it defines the size of window.
		 *
		 * @return the size of JFrame.
		 */
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(650, 650);
		}

		/**
		 * It is an mouse adapter class to process the mouse operation we only use mouse
		 * click.
		 */
		public class checkHandler extends MouseAdapter {
			
			/**
			 * This method will be invoked when an action occurs.
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				
				String obtain = e.getComponent().getName();
				if (obtain.equals("OK")) {
					
					LinkedList<Card> cardlist = delete();
					
					model.cardArmy(player, cardlist, true);
					frame.dispose();
				} else {
					JCheckBox b = (JCheckBox) e.getComponent();
					if (b.isSelected()) {
						boxes.put(b.getName(), b.getText());
						calculate();

					} else {
						boxes.remove(b.getName());
						calculate();
					}

				}
			}

		}

	}

}
