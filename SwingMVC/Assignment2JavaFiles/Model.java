import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model{
		public String upc;
		public String name;
		public double price;

		public Model(String upc, String name, double price){
			this.upc = upc;
			this.name = name;
			this.price = price;
		}

		private final Map<String, Model> catalog = new HashMap<>(); // UPC -> Product
		private final List<Model> scannedItems = new ArrayList<>();
		private double subtotal = 0.0;

		public void loadFromFile(String filePath) throws IOException {
			try (java.util.Scanner scan = new java.util.Scanner(new java.io.File(filePath), java.nio.charset.StandardCharsets.UTF_8)) {				while (scan.hasNextLine()) {
					String line = scan.nextLine().trim();
					if (line.isEmpty()) continue;
					if (line.toLowerCase().startsWith("upc")) continue; // skip header

					// Expect: UPC  ProductName  Price
					// Support multi-word names by taking first as UPC, last as price, middle as name
					String[] parts = line.split("\\s+");
					if (parts.length < 3) continue;

					String upc = cleanUPC(parts[0]);
					String priceToken = parts[parts.length - 1].replace("$", "").replace(",", "");
					double price;
					try {
						price = Double.parseDouble(priceToken);
					} catch (NumberFormatException nfe) {
						continue;
					}

					StringBuilder name = new StringBuilder();
					for (int i = 1; i < parts.length - 1; i++) {
						if (name.length() > 0) name.append(' ');
						name.append(parts[i]);
					}

					//creating a product object with the information parsed from file


					Model p = new Model(upc, name.toString(), price);
					catalog.put(upc, p);
				}//loop end

			} //try end
		}//method end

		public Model addItem(String upc) {
			upc = cleanUPC(upc);
			Model p = catalog.get(upc);
			if (p != null) {
				scannedItems.add(p);
				subtotal += p.price;
			}
			return p;
		}

		private static String cleanUPC(String s) {
		if (s == null) return null;
		// strip UTF-8 BOM if present and trim spaces
		if (!s.isEmpty() && s.charAt(0) == '\uFEFF') s = s.substring(1);
		return s.trim();
		}


	//Current running subtotal
		public double getSubtotal() {
			return subtotal;
		}

		//view of scanned items list
		public List<Model> getScannedItems() {
			return new ArrayList<>(scannedItems);
		}

	}


