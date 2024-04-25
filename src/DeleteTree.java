import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Constants.Const;

public class DeleteTree {
	private File file = new File(Const.TREETEXTFILEPATH);

	// empty constructor
	public DeleteTree() {

	}

	public String deleteTreeByID(String treeID) {
		// set default value of return message to ID not found
		// and only update if ID is found
		String message = "ID not found";
		try {
			// Read all lines from the file
			List<String> lines = new ArrayList<>();
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
			reader.close();

			// Find and remove the line with the specified tree ID
			Iterator<String> iterator = lines.iterator();
			while (iterator.hasNext()) {
				String currentLine = iterator.next();
				if (currentLine.startsWith(treeID + ",")) {
					iterator.remove();

					// update return message
					message = "Tree Deleted";

					// assume ID is unique so no need to continue loop
					break;
				}
			}

			// Write the updated lines back to the file
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			for (String updatedLine : lines) {
				writer.write(updatedLine);
				writer.newLine();
			}
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();

		}

		return message;
	}
}