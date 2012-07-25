package naivebayes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NaiveBayes {

	public HashMap<String, Integer> vocabulario = new HashMap<String, Integer>();
	public HashMap<String, Integer> countDocByKlass = new HashMap<String, Integer>();
	public HashMap<String, Integer> countWordByKlass = new HashMap<String, Integer>();
	public HashMap<String, HashMap<String, Integer>> mapVocaByKlass = new HashMap<String, HashMap<String, Integer>>();
	public HashSet<String> voc = new HashSet<String>();
	private Integer aux;
	private Integer nrDocs;

	public Boolean inTrain = true;
	public double prior = 0.0;

	/**
	 * Adicionar exemplos de textos para as categorias.
	 **/
	public void addExample(String klass, List<String> words) {

		HashSet<String> noDupli = new HashSet<String>();
		for (String w : words) {
			noDupli.add(w);
		}

		vocabulario = (mapVocaByKlass.containsKey(klass) ? mapVocaByKlass
				.get(klass) : new HashMap<String, Integer>());
		for (String w : noDupli) {
			aux = (vocabulario.containsKey(w) ? vocabulario.get(w) : 0);
			vocabulario.put(w, ++aux);
			aux = (countWordByKlass.containsKey(klass) ? countWordByKlass
					.get(klass) : 0);
			countWordByKlass.put(klass, ++aux);
		}
		mapVocaByKlass.put(klass, vocabulario);

		aux = (countDocByKlass.containsKey(klass) ? countDocByKlass.get(klass)
				: 0);
		countDocByKlass.put(klass, ++aux);
		inTrain = true;
	}

	/**
	 * Dado um texto, a qual classe ele pertence?
	 */
	public String classify(List<String> words) {
		if (inTrain) {
			prepareToClassify();
			inTrain = false;
		}

		HashSet<String> noDupli = new HashSet<String>();
		for (String w : words) {
			noDupli.add(w);
		}

		double best = -100000;
		Integer countAux = 0;
		String bestKlass = "";

		// System.out.println("[DEBUG]\t---");
		for (String kl : countWordByKlass.keySet()) {
			Integer totalWordInKlass = countWordByKlass.get(kl);
			vocabulario = mapVocaByKlass.get(kl);
			double score = 0.0;
			double probability = 0.0;

			// System.out.println("[DEBUG]\t Total word in class (" + kl +
			// ") : " + vocabulario.size());

			for (String word : noDupli) {
				countAux = vocabulario.containsKey(word) ? vocabulario
						.get(word) : 0;
				// System.out.println("[DEBUG]\t Total word in class (" + word +
				// ") : " + countAux);

				probability = Math.log(((double) (countAux + 1))
						/ ((totalWordInKlass + voc.size())));
				score += probability;
			}
			score = score
					+ Math.log((((double) countDocByKlass.get(kl) / nrDocs)));

			// System.out.println("[DEBUG]\t klass (" + kl + ") : " + score);
			if (score > best) {
				best = score;
				bestKlass = kl;
			}
		}

		// System.out.println("[DEBUG]\tmelhor klass:" + bestKlass);
		return bestKlass;
		/*
		 * if(new Random().nextDouble() < 0.5) { return "pos"; } else { return
		 * "neg"; }
		 */

	}

	private void prepareToClassify() {

		// System.out.println("[DEBUG]\tDados do Treinamento.:" );

		nrDocs = 0;
		voc = new HashSet<String>();
		for (String kl : countDocByKlass.keySet()) {
			nrDocs += countDocByKlass.get(kl);

			vocabulario = mapVocaByKlass.get(kl);
			for (String w : vocabulario.keySet()) {
				voc.add(w);
			}

			System.out.print("[DEBUG]\tNumero de docs do treino (" + kl
					+ ") \t : " + countDocByKlass.get(kl));
			System.out.println("\t Vocabulario(" + kl + ") : "
					+ vocabulario.size());
		}

		System.out.println("[DEBUG]\tNumero de classes....:"
				+ countDocByKlass.size());
		System.out.println("[DEBUG]\tNumero de Docs.......:" + nrDocs);
		System.out.println("[DEBUG]\tTam do Vocabulario...:" + voc.size());
	}

	public Set<String> getCategories() {
		return countWordByKlass.keySet();
	}
}
