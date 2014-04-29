package chat.client;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import chat.IMessageVisitor;
import chat.messages.AMessage;

public class ClientFilter extends Observable {

	private IMessageVisitor inFilter;

	private IMessageVisitor outFilter;

	public ClientFilter(List<IMessageVisitor> in_plugins,
			List<IMessageVisitor> out_plugins) {
		super();

		List<IMessageVisitor> inList = new LinkedList<IMessageVisitor>();
		List<IMessageVisitor> outList = new LinkedList<IMessageVisitor>();

		inList.addAll(in_plugins);
		outList.addAll(out_plugins);

		/*
		 * Reduce in and outlist
		 */
		if (!inList.isEmpty()) {
			inFilter = inList.remove(0);
			IMessageVisitor prev = inFilter;
			for (IMessageVisitor v : inList) {
				prev.setNextVisitor(v);
				prev = v;
			}
		}

		if (!outList.isEmpty()) {
			outFilter = outList.remove(0);
			IMessageVisitor prev = outFilter;
			for (IMessageVisitor v : outList) {
				prev.setNextVisitor(v);
				prev = v;
			}
		}
	}

	public void incoming(AMessage message) {

		/*
		 * Start to filter
		 */
		if (inFilter != null)
			message.accept(inFilter);
	}

	public void outgoing(AMessage message) {

		/*
		 * Start filtering
		 */
		if (outFilter != null)
			message.accept(outFilter);
	}

}
