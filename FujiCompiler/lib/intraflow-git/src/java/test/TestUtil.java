package test;

import java.util.ArrayList;
import java.util.Iterator;

import AST.*;

public class TestUtil {

	/**
	 * Prints the control flow of a body declaration. 
	 * @param root The root of the body declaration.
	 * @return The flow as a string.
	 */
	public static String printCFG(BodyDecl root) {
		StringBuffer res = new StringBuffer();
		SmallSet<CFGNode> visitedList = SmallSet.mutable();
		ArrayList<CFGNode> worklist = new ArrayList<CFGNode>();
		CFGEntry entry = root.entry();
		worklist.add(entry);
		visitedList.add(entry);
		while (!worklist.isEmpty()) {
			CFGNode node = worklist.remove(0);
			String succString = "";
			if (node.getDotId() > -1)
				succString = succToString(node);
			if (!succString.isEmpty())
				res.append("    ").append(succString);
			Iterator<CFGNode> itr = node.succ().iterator();
			while (itr.hasNext()) {
				CFGNode succ = (CFGNode)itr.next();
				if (!visitedList.contains(succ)) {
					worklist.add(succ);
					visitedList.add(succ);
				}
			}
		}
		return res.toString();
	}
	
	private static String succToString(CFGNode node) {
		StringBuffer res = new StringBuffer();
		for (CFGNode succ : node.succ()) {
			if (succ.getDotId() > -1) {
				res.append('n').append(node.getDotId());
				if (!(node instanceof CFGEntry) && !(node instanceof CFGExit))
					res.append(":succ");
				res.append(" -> n").append(succ.getDotId());
				if (!(succ instanceof CFGEntry) && !(succ instanceof CFGExit))
					res.append(":name");
				res.append(";\n");
			}
		}
		return res.toString();
	}
	
	private static String altSuccToString(CFGNode node) {
		StringBuffer sb = new StringBuffer();
		for (CFGNode succ : node.succ()) {
			int nodeId = node.getDotId() < 2? node.hashCode() : node.getDotId();
			int succId = succ.getDotId() < 2? succ.hashCode() : succ.getDotId();
			sb.append('n').append(nodeId).append(" -> n").append(succId).append(";\n");
			if (node instanceof CFGEntry || node instanceof CFGExit) {
				sb.append("    n").append(nodeId).append("[shape=ellipse, label=\"").append(node.getClass().getName()).append("\"];\n");
			}
			if (succ instanceof CFGEntry || succ instanceof CFGExit) {
				sb.append("    n").append(succId).append("[shape=ellipse, label=\"").append(succ.getClass().getName()).append("\"];\n");
			}
			
		}
		return sb.toString();
	}
	
	/**
	 * Prints the reverse control flow of a body declaration. 
	 * @param root The root of the body declaration.
	 * @return The flow as a string.
	 */
	public static String printReverseCFG(BodyDecl root) {
		StringBuffer res = new StringBuffer();
		SmallSet<CFGNode> visitedList = SmallSet.mutable();
		ArrayList<CFGNode> worklist = new ArrayList<CFGNode>();
		CFGExit exit = root.exit();
		worklist.add(exit);
		visitedList.add(exit);
		while (!worklist.isEmpty()) {
			CFGNode node = worklist.remove(0);
			res.append(predToString(node));
			Iterator<CFGNode> itr = node.succ().iterator();
			while (itr.hasNext()) {
				CFGNode pred = (CFGNode)itr.next();
				if (!visitedList.contains(pred)) {
					worklist.add(pred);
					visitedList.add(pred);
				}
			}
		}
		res.append('}');
		return res.toString();
	}

	private static String predToString(CFGNode node) {
		StringBuffer res = new StringBuffer();
		for (CFGNode pred : node.pred()) {
			res.append(pred.getClass().getSimpleName() + "->" + node.getClass().getSimpleName());
			res.append('\n');
		}
		return res.toString();
	}
}
