package chat.server;

import java.util.LinkedList;
import java.util.List;

import chat.IMessageVisitor;
import chat.messages.AMessage;

public class ServerFilter {
	IMessageVisitor filterChain;

	public ServerFilter(List<IMessageVisitor> inPlugins,
			List<IMessageVisitor> outPlugins) {
		List<IMessageVisitor> inList = new LinkedList<IMessageVisitor>();
		inList.addAll(inPlugins);
		inList.addAll(outPlugins);

		/*
		 * Reduce in and outlist
		 */
		if (!inList.isEmpty()) {
			filterChain = inList.remove(0);
			IMessageVisitor prev = filterChain;
			for (IMessageVisitor v : inList) {
				prev.setNextVisitor(v);
				prev = v;
			}
		}
	}

	public void filter(AMessage message) {
		message.accept(filterChain);
	}
}