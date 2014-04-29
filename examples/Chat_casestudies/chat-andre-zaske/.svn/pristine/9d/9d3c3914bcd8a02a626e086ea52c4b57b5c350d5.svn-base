package network.server;

import network.client.InPacketEvent;
import network.server.Connection;
import network.server.Connection.ConnectionStatus;
import network.server.packets.DataPacket;
import network.server.packets.TextMessage;

public class ServerPacketInHandler {
	private BFilterTree filterTree;

	public ServerPacketInHandler() {
		// Init Spamfilter
		filterTree = new BFilterTree();
		for (String bword : badWords) {
			filterTree.put(bword, bword);
		}
	}

	public void handlePacketIn(InPacketEvent e) {
		boolean continueProcessing = true;
		Connection c = (Connection) e.getSource();
		DataPacket packet = e.getDataPacket();
		ConnectionStatus status = c.getConnnectStatus();
		if (status == ConnectionStatus.AUTHENTICATED) {
			if(packet instanceof TextMessage){
				TextMessage txtMsg = (TextMessage)packet;
				String msg = c.getCiphCoder().decode(txtMsg.getContent());
				if(checkForBadW(msg)){
					continueProcessing = false;
				}
			}		
		}
		if(continueProcessing){
			original(e);
		}
	}

	private boolean checkForBadW(String txt){
		String[] tokens = txt.split("\\W");
		for(String word: tokens){
			if(filterTree.get(word) != null){
				return true;
			}
		}
		return false;
	}
	
	public class BFilterTree {
		private static final int M = 4; // max children per B-tree node = M-1

		private Node root; // root of the B-tree
		private int HT; // height of the B-tree
		private int N; // number of key-value pairs in the B-tree

		// helper B-tree node data type
		private class Node {
			private int m; // number of children
			private Entry[] children = new Entry[M]; // the array of children

			private Node(int k) {
				m = k;
			} // create a node with k children
		}

		// internal nodes: only use key and next
		// external nodes: only use key and value
		private class Entry {
			private String key;
			private String value;
			private Node next; // helper field to iterate over array entries

			public Entry(String key, String value, Node next) {
				this.key = key;
				this.value = value;
				this.next = next;
			}
		}

		// constructor
		public BFilterTree() {
			root = new Node(0);
		}

		// return number of key-value pairs in the B-tree
		public int size() {
			return N;
		}

		// return height of B-tree
		public int height() {
			return HT;
		}

		// search for given key, return associated value; return null if no such
		// key
		public String get(String key) {
			return search(root, key, HT);
		}

		private String search(Node x, String key, int ht) {
			Entry[] children = x.children;

			// external node
			if (ht == 0) {
				for (int j = 0; j < x.m; j++) {
					if (eq(key, children[j].key))
						return children[j].value;
				}
			}

			// internal node
			else {
				for (int j = 0; j < x.m; j++) {
					if (j + 1 == x.m || less(key, children[j + 1].key))
						return search(children[j].next, key, ht - 1);
				}
			}
			return null;
		}

		// insert key-value pair
		// add code to check for duplicate keys
		public void put(String key, String value) {
			Node u = insert(root, key, value, HT);
			N++;
			if (u == null)
				return;

			// need to split root
			Node t = new Node(2);
			t.children[0] = new Entry(root.children[0].key, null, root);
			t.children[1] = new Entry(u.children[0].key, null, u);
			root = t;
			HT++;
		}

		private Node insert(Node h, String key, String value, int ht) {
			int j;
			Entry t = new Entry(key, value, null);

			// external node
			if (ht == 0) {
				for (j = 0; j < h.m; j++) {
					if (less(key, h.children[j].key))
						break;
				}
			}

			// internal node
			else {
				for (j = 0; j < h.m; j++) {
					if ((j + 1 == h.m) || less(key, h.children[j + 1].key)) {
						Node u = insert(h.children[j++].next, key, value,
								ht - 1);
						if (u == null)
							return null;
						t.key = u.children[0].key;
						t.next = u;
						break;
					}
				}
			}

			for (int i = h.m; i > j; i--)
				h.children[i] = h.children[i - 1];
			h.children[j] = t;
			h.m++;
			if (h.m < M)
				return null;
			else
				return split(h);
		}

		// split node in half
		private Node split(Node h) {
			Node t = new Node(M / 2);
			h.m = M / 2;
			for (int j = 0; j < M / 2; j++)
				t.children[j] = h.children[M / 2 + j];
			return t;
		}

		// for debugging
		public String toString() {
			return toString(root, HT, "") + "\n";
		}

		private String toString(Node h, int ht, String indent) {
			String s = "";
			Entry[] children = h.children;

			if (ht == 0) {
				for (int j = 0; j < h.m; j++) {
					s += indent + children[j].key + " " + children[j].value
							+ "\n";
				}
			} else {
				for (int j = 0; j < h.m; j++) {
					if (j > 0)
						s += indent + "(" + children[j].key + ")\n";
					s += toString(children[j].next, ht - 1, indent + "     ");
				}
			}
			return s;
		}

		// comparison functions - make Comparable instead of Key to avoid casts
		private boolean less(String k1, String k2) {
			return k1.compareTo(k2) < 0;
		}

		private boolean eq(String k1, String k2) {
			return k1.compareTo(k2) == 0;
		}

	}

	private static String[] badWords = new String[] { "acne", "adipex",
			"adult", "advertisement", "advertising", "advicer", "allergies",
			"amazing new discovery", "ambien", "as seen on tv", "asthma",
			"auto loan", "auto loans", "baccarrat", "bachelor", "beat stress",
			"be your own boss", "bllogspot", "booker", "botox", "burn fat",
			"buy now", "buy online", "call anywhere", "came up a winner",
			"career opportunity", "career singles", "carisoprodol", "casino",
			"casinos", "chatroom", "cialis", "click here", "click to play",
			"click to win", "credit card", "cwas", "cyclen", "cyclobenzaprine",
			"dating", "day-trading", "debt free", "degree program",
			"depression", "discreet meeting", "discreet meetings",
			"discreet ordering", "doctor approved", "doctor prescribed",
			"earn a college degree", "earn a degree", "earn big",
			"earn extra money", "earning potential", "easy money",
			"eliminate your debt", "escorts", "fast delivery",
			"find someone special", "find your match", "fioricet",
			"fire your boss", "fountain of youth", "free cell phone",
			"free degree", "free diploma", "free game", "free games",
			"free gas", "free gift", "free list", "free listing",
			"free minutes", "free money", "free of debt", "free offer",
			"free phone", "free reading", "free readings",
			"free screen savers", "free screensavers", "freenet-shopping",
			"get out of debt", "get results", "get results now",
			"get rich quick", "get your reading", "great discounts",
			"health products", "heartburn", "higher income", "home owner",
			"home workers", "homeworkers", "hot deals", "housewife",
			"housewives", "incest", "insurance", "investment", "investor",
			"ionamin", "job search", "join now", "just released", "levitra",
			"loose weight", "low interest", "low interest rate",
			"low interest rates", "low prices", "macinstruct", "mail list",
			"mailing list", "make a living", "make money",
			"make money at home", "matchmaker", "matchmaking", "medications",
			"meds", "meet new singles", "meet singles", "meet someone special",
			"meridia", "million dollars", "millions of dollars", "more income",
			"morgage", "mortgage", "muscle relaxants", "new product", "oem",
			"oem software", "-online", "online gambling", "online-gambling",
			"pain relief", "paxil", "pharmacy", "phendimetrazine",
			"phentamine", "phentermine", "pheramones", "pherimones",
			"photos of singles", "platinum-celebs", "poker-chip", "poze",
			"prescription", "privacy assured", "product for less",
			"products for less", "protect yourself", "psychic", "quit smoking",
			"quit your job", "recover your losses", "sale", "save",
			"scratch and win", "scratch off", "scratch-off",
			"searching for you", "sell now", "sexual health", "sign up",
			"sign up now", "signup", "sleeping disorders", "slot-machine",
			"soma", "stock quotes", "stop paying", "substantial income",
			"super deal", "super deals", "taboo", "take a survey",
			"take surveys", "tarot", "teen", "teenage", "teens", "tenuate",
			"tramadol", "trim-spa", "triple-x", "tripple x", "tripplex",
			"ultram", "university diploma", "venture capitol", "viagara",
			"viagra", "vioxx", "web cam", "weight reduction", "win now",
			"winner confirmation", "winning number", "work at home",
			"worry free", "worry-free", "x rated", "x x x", "xanax", "xenical",
			"x-rated", "xxx", "you have won", "you won", "You're a winner",
			"zero percent", "zolus" };

}
