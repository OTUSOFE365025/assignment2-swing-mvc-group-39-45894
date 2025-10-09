import java.io.IOException;

public class MySwingMVCApp {
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(() -> {
			Model cashRegister = new Model(null, null, 0.0);
            try {
                cashRegister.loadFromFile("products.txt");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            View view = new View();

            Scanner scanner = null;
            try {
                scanner = new Scanner();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                Controller c = new Controller(cashRegister, view, scanner);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
	}
}