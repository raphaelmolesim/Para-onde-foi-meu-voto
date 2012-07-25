package naivebayes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import basic.Corpus;
import basic.Document;
import basic.Util;

public class ClassifierNB {
	NaiveBayes naiveBayes;
	 private static List<String> stopList = readFile(new File("/home/wesley/var/pln/textcat/stopwords.txt"));

	public ClassifierNB() {
		naiveBayes = new NaiveBayes();
	}

	public static void main(String[] args) {
		String folder = "/home/wesley/var/pln/textcat/";

		System.out.println("Lendo arquivos...");
		Corpus corpus = new Corpus();
//		corpus.addFileToTest (new File(folder + "Bosque_CF_8.0.ad-categorias-teste.csv"));
//		corpus.addFileToTrain(new File(folder + "Bosque_CF_8.0.ad-categorias-treino.csv"));
//		corpus.addFileToTest (new File(folder + "transform1-teste-menor.tsv"));
//		corpus.addFileToTrain(new File(folder + "transform1-treino-menor.tsv"));
//		corpus.addFileToTest (new File(folder + "transform1-teste.tsv"));
//		corpus.addFileToTrain(new File(folder + "transform1-treino.tsv"));
//		corpus.readFile(new File(folder + "transform1-treino.tsv"));
//		corpus.readFile(new File(folder + "transform1-teste.tsv"));
//		corpus.readFile(new File(folder + "Bosque_CF_8.0.ad-categorias-teste.csv"));
//		corpus.readFile(new File(folder + "Bosque_CF_8.0.ad-categorias-treino.csv"));
//		corpus.readFile(new File(folder + "projetos9horas.csv"));
		corpus.readFile(new File(folder + "fazido9horas_ava.csv"));
//		corpus.readFile(new File(folder + "fazido9horas_ava-menor.csv"));

		ClassifierNB engine = new ClassifierNB();

		System.out.println("Treinando...");
		engine.train(corpus.getTrain());

		System.out.println("Avaliando...");
		engine.eval(corpus.getTest());

	}

	private void eval(List<Document> test) {
		int acertos = 0;
		int erros = 0;
		Map<String,Integer> errMap = new HashMap<String, Integer>();
		for (Document d : test) {
			String guest = getBestCategory(d.getText());
			if (guest.equals(d.getCategory())) {
				acertos++;
			} else {
				erros++;
			}
			String k = "["+d.getCategory()+"]:["+guest+"]";
			Integer aux = (errMap.containsKey(k) ? errMap.get(k) : 0);
			errMap.put(k, ++aux);
		}
		System.out.println("Matriz de Confusão");
		printConfusionMatrix(errMap);
		System.out.println("---------------[target : guest]");
//		System.out.println(errMap);
		System.out.println("Acertos:" + acertos);
		System.out.println("Erros..:" + erros);
		
	}

	private void printConfusionMatrix(Map<String, Integer> errMap) {
//		Set<String> cats = naiveBayes.getCategories();
		List<String> cats = new ArrayList<String>();
		cats.addAll(naiveBayes.getCategories());
		System.out.print(" ");
		for (String guest : cats) {
			System.out.print("\t" + guest.substring(0, 3 ));
		}
		System.out.println("\ttotal"  );
		for (String target : cats) {
			System.out.print( target.substring(0, 3 ) );
			Integer totalCat = 0;
			for (String guest : cats) {
				String k = "["+target+"]:["+guest+"]";
				Integer aux = (errMap.containsKey(k) ? errMap.get(k) : 0);
				System.out.print("\t" + (aux>0?aux:"."));
				totalCat +=aux;
			}			
			System.out.println( "\t" + totalCat );
		}
		
	}

	private void train(List<Document> train) {
		for (Document document : train) {
//			naiveBayes.addExample(document.getCategory(), filterStopWords(Util
//					.segmentWords(document.getText())));
//			naiveBayes.addExample(document.getCategory(), Util
//					.segmentWords(document.getText())));
			naiveBayes.addExample(document.getCategory(), Util
					.segmentWords(cleanText(document.getText())));
		}
	}

	private String getBestCategory(String textToClassify) {

//		return naiveBayes.classify(filterStopWords(Util
//				.segmentWords(cleanText(textToClassify))));
//		return naiveBayes.classify(filterStopWords(Util
//				.segmentWords(textToClassify)));
		return naiveBayes.classify(Util
				.segmentWords(cleanText(textToClassify)));
	}
	
	  /** 
	   * Carregar um arquivo e devolver uma lista de palavras 
	   **/
	  private static List<String> readFile(File f) {
	    try {
	      StringBuilder contents = new StringBuilder();

	      BufferedReader input = new BufferedReader(new FileReader(f));
	      for(String line = input.readLine(); line != null; line = input.readLine()) {
	        contents.append(line);
	        contents.append("\n");
	      }
	      input.close();

	      return Util.segmentWords(contents.toString());
	      
	    } catch(IOException e) {
	      e.printStackTrace();
	      System.exit(1);
	      return null;
	    } 
	  }
	  
	  /**
	   * Remover qualquer stop words ou punctuaçao de uma lists de palavras
	   **/
	  public static List<String> filterStopWords(List<String> words) {
	    List<String> filtered = new ArrayList<String>();
	    for (String word :words) {
	      if (!stopList.contains(word) && !word.matches(".*\\W+.*")) {
		filtered.add(word);
	      }
	    }
	    return filtered;
	  }

	  public static String cleanText(String text) {
			String ret = text.replace("#", " ");
			ret = ret.replace("%", " ");
			ret = ret.replace(".", " ");
	    return ret;
	  }

}
