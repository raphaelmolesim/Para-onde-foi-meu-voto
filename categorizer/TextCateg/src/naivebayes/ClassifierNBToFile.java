package naivebayes;

import java.io.File;
import java.util.List;

import basic.Corpus;
import basic.Document;
import basic.Util;

public class ClassifierNBToFile {
	NaiveBayes naiveBayes;

	public ClassifierNBToFile() {
		naiveBayes = new NaiveBayes();
	}

	public static void main(String[] args) {
		String folder = "/home/wesley/var/pln/textcat/";

		System.out.println("Lendo arquivos...");
		Corpus corpus = new Corpus();
//		corpus.readFile(new File(folder + "fazido9horas.csv"));
		corpus.readFile(new File(folder + "base-10-07-12.csv"));
		
		// corpus.readFile(new File(folder + "projetos9horas.csv"));

		ClassifierNBToFile engine = new ClassifierNBToFile();

		System.out.println("Treinando...");
		List<Document> docsToTrain = corpus.getCategories("ambiente",
				"aumento", "cultura", "data", "dev", "direito", "edu",
				"espaco_pub", "habita", "lixo", "muda_nome", "outros",
				"regulamentacao", "saude", "seguranca", "transito");
		engine.train(docsToTrain);

		System.out.println("Etiquetando...");
		List<Document> docsToTagger = corpus.getCategories("TBD");
		engine.tagger(docsToTagger);

		for (Document d : docsToTrain) {
			System.out.print(d.getCategory() + " \t");
			System.out.println(d);
		}

		System.out.println("Docs etiquetados.:" + docsToTagger.size());

	}

	private void tagger(List<Document> docs) {
		for (Document d : docs) {
			String guest = getBestCategory(d.getText());
			System.out.print(guest + "\t");
			System.out.println(d);
		}
	}

	private void train(List<Document> train) {
		for (Document document : train) {
			naiveBayes.addExample(document.getCategory(), Util
					.segmentWords(cleanText(document.getText())));
		}
	}

	private String getBestCategory(String textToClassify) {
		return naiveBayes
				.classify(Util.segmentWords(cleanText(textToClassify)));
	}

	public static String cleanText(String text) {
		String ret = text.replace("#", " ");
		ret = ret.replace("%", " ");
		ret = ret.replace(".", " ");
		return ret;
	}

}
