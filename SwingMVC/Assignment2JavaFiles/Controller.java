import javax.swing.JOptionPane;
import java.io.IOException;

public class Controller {
	 private Model cashRegister;
	 private View view;
	 private Scanner scanner;

	 public Controller(Model cashRegister, View view, Scanner scanner) throws IOException {
	  this.cashRegister = cashRegister;
	  this.view = view;
	  this.scanner = scanner;
	  cashRegister.loadFromFile("products.txt");
	  initController();
	 }

	 public void initController() {
		 scanner.getScanButton().addActionListener(e -> duringScan());
	 }

	private void duringScan() {
		String upc = scanner.generateUPC();

		if (upc == null || upc.isEmpty()) return;

		// addItem returns a Product object
		Model added = cashRegister.addItem(upc);
		if (added == null) {
			JOptionPane.showMessageDialog(
					null,
					"Unknown UPC: " + upc,
					"Scan Error",
					JOptionPane.WARNING_MESSAGE
			);
			return;
		}

		view.update(cashRegister.getScannedItems(), cashRegister.getSubtotal());

	}
	}



