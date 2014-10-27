package superserial;

    
//import Methods.Individual;
//import Methods.Generation;
    import java.io.*;  //for reading to a from files
    import java.util.Arrays;
    import java.util.Comparator;
    import org.jfree.chart.ChartFactory;
    import org.jfree.chart.ChartUtilities;
    import org.jfree.chart.JFreeChart;
    import org.jfree.data.category.DefaultCategoryDataset;
    import org.jfree.chart.plot.PlotOrientation;
    import org.jfree.util.PublicCloneable;

public class methods{

    
    
    /**
     * Prints to screen the individual's genetic data -to be modified to print
     * data to file for storage
     *
     * @param cromOne - First chromosome's data
     * @param cromTwo - Second chromosome's data
     * @param individual - Which individual number is being recorded
     * @param Fitness - The fitness value of the individual
     * @param file - File to be written to.
     */
    public static void geneiticsTable(int cromOne[], int cromTwo[], int individual, double Fitness, String file) {  // for human reading
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("saves/" + file));
            out.write("Individual number: " + individual + "\n");
            out.write("chromosome one   chromosome two\n");
            for (int i = 0; i < 10; i++) {
                out.write(cromOne[i] + "                 " + cromTwo[i] + "\n");
            }
            out.write("Fitness: " + Fitness + "\n");
            out.close();
        } catch (IOException e) {
        }
    }
    
    
    public static void fitnessTable(){
        
        
    }

    /**
     * Saves the genetics of one generation to a binary file. -----OBSOLITE-----
     *
     * @param genetics
     * @param individuals
     * @param generation
     * @param Fitness
     * @param file
     */
    @Deprecated
    public static void geneticsSave(int[][] genetics, int individuals, int generation, double Fitness, String file) {    // for machine reading
        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream("saves/" + file + "/" + generation));
            out.writeInt(generation);
            out.writeInt(individuals);
            for (int n = 0; n < individuals; n++) {
                for (int i = 0; i < 10; i++) {
                    out.writeInt(genetics[i][0]);
                }
                for (int i = 0; i < 10; i++) {
                    out.writeInt(genetics[i][1]);
                }
                for (int i = 0; i < 8; i++) {
                    out.writeDouble(Fitness);
                }
            }
            out.close();
        } catch (IOException e) {
        }
    }

    /**
     * Saves the genetics of one generation to a binary file.
     *
     * @param gen - generation Object to be saved.
     * @param file - file to be saved to.
     */
    public static void generationSave(Generation gen, String file) {    // for machine reading
        try {
            File OF=new File("saves" + File.separator + file + File.separator + gen.getGeneration() + ".bot");
            OF.getParentFile().mkdirs();
            OF.createNewFile();
            DataOutputStream out = new DataOutputStream(new FileOutputStream(OF));
            out.writeInt(gen.getGeneration());
            out.writeInt(gen.getNumIndividuals());
            for (int n = 0; n < gen.getNumIndividuals()-1; n++) {
                Individual individual = gen.getIndividual(n);
                int[][] genetics = individual.getGenetics().clone();
                for (int i = 0; i < 10; i++) {
                    out.writeInt(genetics[0][i]);
                }
                for (int i = 0; i < 10; i++) {
                    out.writeInt(genetics[1][i]);
                }
                int[] sensorTimes = individual.getSensorTimes();
                for (int i = 0; i < 8; i++) {
                    out.writeInt(sensorTimes[i]);
                }
            }
            out.close();
        }catch (IOException e) {
            System.out.println(e.toString());
        }catch (SecurityException e){
            System.out.println(e.toString());
        }
    }

    /**
     * Retrieves the data of a single generation from a binary file Use try{ ...
     * }catch{ ... } Suggested usage: Generation generation = new
     * Generation("file.save");
     *
     * @param file - file to be read from
     * @return - Object Generation
     * @throws IOException
     */
    public static Generation getGeneration(String file) throws IOException {
        int generationNumber;
        int individuals;
        int[][] genetics = new int[2][10];
        int[] sensorTimes = new int[8];
        try {
            DataInputStream in = new DataInputStream(new FileInputStream("saves" + File.separator + file + ".bot"));
            generationNumber = in.readInt();
            individuals = in.readInt();
            Generation gen = new Generation(individuals, generationNumber);
            for (int n = 0; n < individuals; n++) {
                for (int i = 0; i < 10; i++) {
                    genetics[0][i] = in.readInt();
                }
                for (int i = 0; i < 10; i++) {
                    genetics[1][i] = in.readInt();
                }
                for (int i = 0; i < 8; i++) {
                    sensorTimes[i] = in.readInt();
                }
                Individual individual = new Individual(genetics, n, sensorTimes);
                individual.recalcFitness();
                gen.addIndividual(individual);
            }
            in.close();

            return gen;

        } catch (IOException e) {
            System.out.println("Failed to read from file");
            throw e;
        }

    }
    
    
    
    
    /**
     * graphs the progress of generations
     * AVERAGE FITNESS ignoring 0's
     *
     * @param gen - Array of generations to be graphed
     * @param file - where the graph is stored
     */
    public static void graphAverageGenerations(Generation[] gen, String file) {
        double fit;
        double[] fitaves = new double[gen.length];
        for (int i = 0; i < gen.length; i++) {
            fit = 0;
            for (int j = 0; j < gen[i].getNumIndividuals(); j++) {
                fit = fit + gen[i].getIndividual(j).getFitness();
            }
            fitaves[i] = fit / gen[i].getNumIndividuals();
        }
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        for (int i = 0; i < fitaves.length; i++) {
            dataSet.setValue((Number) fitaves[i], "Fitness", gen[i].getGeneration());
        }
        JFreeChart chart = ChartFactory.createLineChart("Average Fitness of Generations", "Generation", "Average Fitness", dataSet);
        File out_file = new File("saves"+ File.separator + file);
        if (!out_file.isDirectory() || !out_file.exists()) {
            out_file.mkdir();
        }
        try {
            ChartUtilities.saveChartAsJPEG(out_file, chart, 500, 500);
        } catch (IOException e) {
            System.err.print("failed to save graph\n");
        }
    }
    
    
    /**
     * graphs the progress of generations
     * MAXIMUM FITNESS
     * @param gen - Array of generations to be graphed
     * @param file - where the graph is stored
     */
    public static void graphMaxGenerations(Generation[] gen, String file) {
        double max;
        double[] fitaves = new double[gen.length];
        for (int i = 0; i < gen.length; i++) {
            max = 0;
            for (int j = 0; j < gen[i].getNumIndividuals(); j++) {
                if(gen[i].getIndividual(j).getFitness()>max){
                    max=gen[i].getIndividual(j).getFitness();
                }
            }
            fitaves[i] = max;
        }
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        for (int i = 0; i < fitaves.length; i++) {
            dataSet.setValue((Number) fitaves[i], "Fitness", gen[i].getGeneration());
        }
        JFreeChart chart = ChartFactory.createLineChart("Maximum Fitness of Generations", "Generation", "Maximum Fitness", dataSet);
        File out_file = new File("saves"+ File.separator + file);
        if (!out_file.isDirectory() || !out_file.exists()) {
            out_file.mkdir();
        }
        try {
            ChartUtilities.saveChartAsJPEG(out_file, chart, 500, 500);
        } catch (IOException e) {
            System.err.print("failed to save graph\n");
        }
    }

    /**
     * graphs the progress through generations from the save files
     *
     * @param files - array of strings specifying the files to be read from
     * @deprecated 
     */
    public static void graphGenerationsFromFiles(String[] files) {
        double fit;
        double[] fitaves = new double[files.length];
        int[] gens = new int[files.length];
        try {
            for (int i = 0; i < files.length; i++) {
                Generation gen = getGeneration(files[i]);
                fit = 0;
                gens[i] = gen.getGeneration();
                for (int j = 0; j < gen.getNumIndividuals(); j++) {
                    fit = fit + gen.getIndividual(j).getFitness();
                }
                fitaves[i] = fit / gen.getNumIndividuals();
            }
        } catch (IOException e) {
            System.err.print("Failed to get all Generations");
        }
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        for (int i = 0; i < fitaves.length; i++) {
            dataSet.setValue((Number) fitaves[i], "Fitness", gens[i]);
        }
        JFreeChart chart = ChartFactory.createLineChart("title", "Generation", "Average Fitness", dataSet);
        String save = "saves/defaultgraphGenerationsFromFilessave.jpg";
        try {
            ChartUtilities.saveChartAsJPEG(new File(save), chart, 500, 500);
        } catch (IOException e) {
            System.err.print("failed to save graph\n");
        }
    }

    /**
     * graphs the fitness of the individuals in a generation
     *
     * @param gen - the generation to be graphed, type Generation
     * @deprecated 
     */
    public static void graphGenerationFitness(Generation gen) {
        int num = gen.getNumIndividuals();
        Individual individual[] = new Individual[num];
        for (int i = 0; i < num; i++) {
            individual[i] = gen.getIndividual(i);
        }
        Comparator comp = new IndCompare();
        Arrays.sort(individual, comp);
        //now to graph...
        DefaultCategoryDataset dataSet;
        dataSet = new DefaultCategoryDataset();
        String legend = "individuals";
        for (int i = 0; i < num; i++) {
            dataSet.setValue((Number) individual[i].getFitness(), legend, individual[i].getNumber());
        }
        JFreeChart chart = ChartFactory.createBarChart("title", "individual", "Fitness", dataSet);

        //save the chart
        String save = "saves/chart.jpg";
        try {
            ChartUtilities.saveChartAsJPEG(new File(save), chart, 500, 500);
        } catch (IOException e) {
            System.err.print("Failed to save chart\n");
        }
    }
}
