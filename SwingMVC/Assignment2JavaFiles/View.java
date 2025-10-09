import javax.swing.*;
import java.awt.*;
import java.util.List;

public class View {
	private final JFrame frame;
	private final DefaultListModel<String> listModel;
	private final JList<String> itemList;
	private final JLabel subtotalLabel;

	public View() {
		frame = new JFrame("Cash Register");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setSize(320, 420);
		frame.setLocation(500, 50);

		// Center: scrollable list of scanned items
		listModel = new DefaultListModel<>();
		itemList = new JList<>(listModel);
		itemList.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		frame.add(new JScrollPane(itemList,
						ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
						ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED),
				BorderLayout.CENTER);

		// Bottom: subtotal label
		subtotalLabel = new JLabel("Subtotal: $0.00");
		JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		bottom.add(subtotalLabel);
		frame.add(bottom, BorderLayout.SOUTH);

		frame.setVisible(true);
	}

	/** Controller calls this after each scan to refresh the UI. */
	public void update(List<Model> items, double subtotal) {
		listModel.clear();
		for (Model p : items) {
			// Align nicely with monospaced font
			listModel.addElement(String.format("%-20s $%6.2f", p.name, p.price));
		}
		subtotalLabel.setText(String.format("Subtotal: $%.2f", subtotal));
		// Auto-scroll to latest item
		int last = listModel.getSize() - 1;
		if (last >= 0) itemList.ensureIndexIsVisible(last);
	}

	// (Optional) Helpers if you want finer-grained updates
	public void appendLine(String line) { listModel.addElement(line); }
	public void setSubtotal(double subtotal) {
		subtotalLabel.setText(String.format("Subtotal: $%.2f", subtotal));
	}

	public JFrame getFrame() { return frame; }
}
