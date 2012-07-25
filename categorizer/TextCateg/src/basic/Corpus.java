package basic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class Corpus {

	private Map<String, List<Document>> corpus;

	public Corpus() {
		corpus = new HashMap<String, List<Document>>();
	}

	private double MAX_TRAIN = 0.9;

	public List<Document> getTrain() {
		List<Document> retorno = new ArrayList<Document>();
		for (Entry<String, List<Document>> entry : corpus.entrySet()) {
			int max = (int) Math.ceil(MAX_TRAIN * entry.getValue().size());
			for (int i = 0; i < max; i++) {
				retorno.add(entry.getValue().get(i));
			}
		}
		System.out.println("[DEBUG]\tNr Docs Train: " + retorno.size());
		return retorno;
	}

	public List<Document> getTest() {
		List<Document> retorno = new ArrayList<Document>();
		for (Entry<String, List<Document>> entry : corpus.entrySet()) {
			int max = (int) Math.ceil(MAX_TRAIN * entry.getValue().size());
			for (int i = max; i < entry.getValue().size(); i++) {
				retorno.add(entry.getValue().get(i));
			}
		}
		System.out.println("[DEBUG]\tNr Docs Test: " + retorno.size());
		return retorno;
	}

	public List<Document> getCategories(String ... categories) {
		List<Document> retorno = new ArrayList<Document>();
		for (String cat : categories) {
			if (corpus.containsKey(cat)) {
				retorno.addAll(corpus.get(cat));
			}
		}
		return retorno;
	}

	public List<Document> getAllDocuments() {
		List<Document> retorno = new ArrayList<Document>();
		for (Entry<String, List<Document>> entry : corpus.entrySet()) {
			retorno.addAll(entry.getValue());
		}
		return retorno;
	}

	private List<Document> getListByMap(Map<String, List<Document>> mapa) {
		List<Document> retorno = new ArrayList<Document>();
		for (Entry<String, List<Document>> entry : mapa.entrySet()) {
			retorno.addAll(entry.getValue());
		}
		return retorno;
	}

	public void readFile(File f) {
		try {

			BufferedReader input = new BufferedReader(new FileReader(f));
			for (String line = input.readLine(); line != null; line = input
					.readLine()) {
				String[] campos = line.split("\t");
				// if (!campos[3].equals("TBD")) {
				Document d = parse(campos);
				addDoc(d);
				// }
			}
			input.close();

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	private Document parse(String[] campos) {
		// Document d = new Document(removeQuotes(campos[3]),
		// removeQuotes(campos[7]));
		String cat = "";
		String texto = "";
//		String id = campos[0];
//		if (campos.length == 3) {
//			cat = removeQuotes(campos[1]).replace(" ", "");
//			texto = removeQuotes(campos[2]);
//		} else if (campos.length == 4) {
//			cat = removeQuotes(campos[1]).replace(" ", "");
//			texto = removeQuotes(campos[2]) + " " + removeQuotes(campos[3]);
//		}
		String id = campos[1];
		if (campos.length == 3) {
			cat = removeQuotes(campos[2]).replace(" ", "");
			texto = removeQuotes(campos[3]);
		} else if (campos.length == 4) {
			cat = removeQuotes(campos[2]).replace(" ", "");
			texto = removeQuotes(campos[3]);
		}
		cat = checkGroups(cat);

		Document d = new Document(cat, texto);
		d.setId(id);

		return d;
		// return new Document(removeQuotes(campos[0]).replace(" ", ""),
		// removeQuotes(campos[1]));
		// return new Document(removeQuotes(campos[3]).replace(" ", ""),
		// removeQuotes(campos[7]));
	}

	private String checkGroups(String cat) {
		String ret = cat;
		if (cat.equals("imposto") || cat.equals("fiscalizacao")) {
			ret = "regulamentacao";
		} else if (cat.equals("financas")) {
			ret = "dev";
		} else if (cat.equals("homenagem")) {
			ret = "cultura";
//		} else if (cat.equals("aumento") || cat.equals("seguranca") || cat.equals("direito") || cat.equals("outros") || cat.equals("lixo") || cat.equals("habita")){
//			ret = "outros";			
//		} else if (cat.equals("regulamentacao")) {
//			ret = "saude";
		}
		return ret;
	}

	private void addDoc(Document d) {
		List<Document> auxList = (corpus.containsKey(d.getCategory()) ? corpus
				.get(d.getCategory()) : new ArrayList<Document>());
		auxList.add(d);
		corpus.put(d.getCategory(), auxList);
	}

	public Set<String> getCategories() {
		return corpus.keySet();
	}

	private static String removeQuotes(String t) {
		return t.replace("\"", "");
	}

}
