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
    
    public static void topSave(Generation gen, String file){
        try{
            File OF=new File("saves" + File.separator + file + File.separator + "TOP.top");
            OF.getParentFile().mkdirs();
            OF.createNewFile();
            DataOutputStream out = new DataOutputStream(new FileOutputStream(OF));
            out.writeInt(gen.getGeneration());
            out.close();
        }catch (IOException e) {
            System.out.println(e.toString());
        }catch (SecurityException e){
            System.out.println(e.toString());
        }
    }
    
    public static int getLatest(String file){
        int i;
        try{
            DataInputStream in = new DataInputStream(new FileInputStream("saves" + File.separator + file + File.separator + "TOP.top"));
            i= in.readInt();
            in.close();
        }catch(IOException e){
            return -1;
        }
        return i;
    }
    
    public static int getLatest(File file){
        int i;
        try{
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            i= in.readInt();
            in.close();
        }catch(IOException e){
            return -1;
        }
        return i;
    }
    
    /**
     * Saves the genetics of one generation to a binary file.
     *
     * @param gen - generation Object to be saved.
     * @param file - file to be saved to.
     */
    public static void generationSave(Generation gen, String file) {    // for machine reading
        topSave(gen,file);
        try {
            File OF=new File("saves" + File.separator + file + File.separator + gen.getGeneration() + ".bot");
            OF.getParentFile().mkdirs();
            OF.createNewFile();
            DataOutputStream out = new DataOutputStream(new FileOutputStream(OF));
            out.writeInt(gen.getGeneration());
            out.writeInt(gen.getNumIndividuals());
            for (int n = 0; n < gen.getNumIndividuals(); n++) {
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
     * @param generation gen number
     * @param file - file to be read from
     * @return - Object Generation
     * @throws IOException
     */
    public static Generation getGeneration(int generation, String file) throws IOException {
        int generationNumber;
        int individuals;
        int[][] genetics = new int[2][10];
        int[] sensorTimes = new int[8];
        try {
            DataInputStream in = new DataInputStream(new FileInputStream("saves" + File.separator + file+ File.separator+ generation + ".bot"));
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
     * Retrieves the data of a single generation from a binary file Use try{ ...
     * }catch{ ... } Suggested usage: Generation generation = new
     * Generation("file.save");
     *
     * @param file - file to be read from
     * @return - Object Generation
     * @throws IOException
     */
    public Generation getGeneration(File file) throws IOException {
        int generationNumber;
        int individuals;
        int[][] genetics = new int[2][10];
        int[] sensorTimes = new int[8];
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            generationNumber = in.readInt();
            individuals = in.readInt();
            Generation gen = new Generation(individuals, generationNumber);
            for (int n = 0; n < individuals; n++) {
                genetics = new int[2][10];
                for (int i = 0; i < 10; i++) {
                    genetics[0][i] = in.readInt();
                }
                for (int i = 0; i < 10; i++) {
                    genetics[1][i] = in.readInt();
                }
                for (int i = 0; i < 8; i++) {
                    sensorTimes[i] = in.readInt();
                }
                
                Individual individual = Individual.class.newInstance();//new Individual(genetics, n, sensorTimes);
                individual.setVals(genetics, n, sensorTimes);
                individual.recalcFitness();
                gen.addIndividual(individual);
            }
            in.close();

            return gen;

        } catch (IOException e) {
            System.out.println("Failed to read from file");
            throw e;
        }catch (InstantiationException|IllegalAccessException e){
            System.out.println("Failed to create new Individual classes");
            return null;
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
                Generation gen = getGeneration(i, files[i]);
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
    
    /**
     * Prints data on all of the generations this top file represents.
     * @param f
     * @return 
     */
    public File averageAbsoluteFitness(File f){
        File out;
        Generation G;
        int genMax, genCur;
        
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(f));
            genMax=in.readInt();
            in.close();
        } catch (IOException e) {
            System.out.println("Failed to read from: "+f.getAbsolutePath());
            genMax=0;
        }
        
        
        
        int individuals, generationNumber;
        int[] sensorTimes= new int[8];
        int[][] genetics= new int[2][10];
        float[] genFitness=new float[genMax];
        String prePath = f.getPath().substring(0, f.getAbsolutePath().lastIndexOf("\\"));
        
        for(genCur=0;genCur<genMax; genCur++){
            try {
                DataInputStream in = new DataInputStream(new FileInputStream(prePath+"\\"+(genCur+1)+".bot"));
                generationNumber = in.readInt();
                individuals = in.readInt();
                G = new Generation(individuals, generationNumber);
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
                    G.addIndividual(individual);
                }
                genFitness[genCur]=G.getAverageFitness();
                in.close();

            } catch (IOException e) {
                System.out.println("Failed to read from: "+prePath+"\\"+(genCur+1)+".bot");
            }
        }
        
        
        
        //now to graph...
        DefaultCategoryDataset dataSet;
        dataSet = new DefaultCategoryDataset();
        String legend = "individuals";
        for (int i = 0; i < genMax; i++) {
            dataSet.setValue((Number) genFitness[i], legend, i+1);
        }
        JFreeChart chart = ChartFactory.createBarChart("averageAbsoluteFitness", "Generations", "Fitness", dataSet);
        String prevPath=f.getPath();
        String save = prevPath.substring(0,prevPath.lastIndexOf("\\"))+"\\chart.jpg";
        try {
            out=new File(save);
            ChartUtilities.saveChartAsJPEG(out , chart, 500, 500);
        } catch (IOException e) {
            out=null;
            System.err.print("Failed to save chart\n");
        }
        return out;
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
        /**
     * Prints data on all of the generations this top file represents.
     * @param f
     * @return 
     */
    public File bestAbsoluteFitness(File f){
        File out;
        Generation G;
        int genMax, genCur;
        
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(f));
            genMax=in.readInt();
            in.close();
        } catch (IOException e) {
            System.out.println("Failed to read from: "+f.getAbsolutePath());
            genMax=0;
        }
        
        
        
        int individuals, generationNumber;
        int[] sensorTimes= new int[8];
        int[][] genetics= new int[2][10];
        float[] genFitness=new float[genMax];
        String prePath = f.getPath().substring(0, f.getAbsolutePath().lastIndexOf("\\"));
        
        for(genCur=0;genCur<genMax; genCur++){
            try {
                DataInputStream in = new DataInputStream(new FileInputStream(prePath+"\\"+(genCur+1)+".bot"));
                generationNumber = in.readInt();
                individuals = in.readInt();
                G = new Generation(individuals, generationNumber);
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
                    G.addIndividual(individual);
                }
                genFitness[genCur]=G.getBestFitness();
                in.close();

            } catch (IOException e) {
                System.out.println("Failed to read from: "+prePath+"\\"+(genCur+1)+".bot");
            }
        }
        
        
        
        //now to graph...
        DefaultCategoryDataset dataSet;
        dataSet = new DefaultCategoryDataset();
        String legend = "individuals";
        for (int i = 0; i < genMax; i++) {
            dataSet.setValue((Number) genFitness[i], legend, i+1);
        }
        JFreeChart chart = ChartFactory.createBarChart("bestAbsoluteFitness", "Generations", "Fitness", dataSet);
        String prevPath=f.getPath();
        String save = prevPath.substring(0,prevPath.lastIndexOf("\\"))+"\\chart.jpg";
        try {
            out=new File(save);
            ChartUtilities.saveChartAsJPEG(out , chart, 500, 500);
        } catch (IOException e) {
            out=null;
            System.err.print("Failed to save chart\n");
        }
        return out;
    }
    //------------------------------------------------------------------------------------------------------------------------------------
     /**
     * Prints data on all of the generations this top file represents.
     * @param f
     * @return 
     */
    public File averageRelativeFitness(File f){
        File out;
        Generation G;
        int genMax, genCur;
        
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(f));
            genMax=in.readInt();
            in.close();
        } catch (IOException e) {
            System.out.println("Failed to read from: "+f.getAbsolutePath());
            genMax=0;
        }
        
        
        
        int individuals, generationNumber;
        int[] sensorTimes= new int[8];
        int[][] genetics= new int[2][10];
        float[] genFitness=new float[genMax];
        String prePath = f.getPath().substring(0, f.getAbsolutePath().lastIndexOf("\\"));
        
        for(genCur=0;genCur<genMax; genCur++){
            try {
                DataInputStream in = new DataInputStream(new FileInputStream(prePath+"\\"+(genCur+1)+".bot"));
                generationNumber = in.readInt();
                individuals = in.readInt();
                G = new Generation(individuals, generationNumber);
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
                    G.addIndividual(individual);
                }
                genFitness[genCur]=G.getAverageFitness();
                in.close();

            } catch (IOException e) {
                System.out.println("Failed to read from: "+prePath+"\\"+(genCur+1)+".bot");
            }
        }
        
        float best=0;
        for(Float Fl:genFitness){
            if(Fl>best)
                best=Fl;
        }
        if(best!=0){
            for(int i=0; i < genFitness.length; i++){
                genFitness[i]/=best;
            }
        }
        
        
        
        //now to graph...
        DefaultCategoryDataset dataSet;
        dataSet = new DefaultCategoryDataset();
        String legend = "individuals";
        for (int i = 0; i < genMax; i++) {
            dataSet.setValue((Number) genFitness[i], legend, i+1);
        }
        JFreeChart chart = ChartFactory.createBarChart("averageRelativeFitness", "Generations", "Fitness", dataSet);
        String prevPath=f.getPath();
        String save = prevPath.substring(0,prevPath.lastIndexOf("\\"))+"\\chart.jpg";
        try {
            out=new File(save);
            ChartUtilities.saveChartAsJPEG(out , chart, 500, 500);
        } catch (IOException e) {
            out=null;
            System.err.print("Failed to save chart\n");
        }
        return out;
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
        /**
     * Prints data on all of the generations this top file represents.
     * @param f
     * @return 
     */
    public File bestRelativeFitness(File f){
        File out;
        Generation G;
        int genMax, genCur;
        
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(f));
            genMax=in.readInt();
            in.close();
        } catch (IOException e) {
            System.out.println("Failed to read from: "+f.getAbsolutePath());
            genMax=0;
        }
        
        
        
        int individuals, generationNumber;
        int[] sensorTimes= new int[8];
        int[][] genetics= new int[2][10];
        float[] genFitness=new float[genMax];
        String prePath = f.getPath().substring(0, f.getAbsolutePath().lastIndexOf("\\"));
        
        for(genCur=0;genCur<genMax; genCur++){
            try {
                DataInputStream in = new DataInputStream(new FileInputStream(prePath+"\\"+(genCur+1)+".bot"));
                generationNumber = in.readInt();
                individuals = in.readInt();
                G = new Generation(individuals, generationNumber);
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
                    G.addIndividual(individual);
                }
                genFitness[genCur]=G.getBestFitness();
                in.close();

            } catch (IOException e) {
                System.out.println("Failed to read from: "+prePath+"\\"+(genCur+1)+".bot");
            }
        }
        
        float best=0;
        for(Float Fl:genFitness){
            if(Fl>best)
                best=Fl;
        }
        if(best!=0){
            for(int i=0; i < genFitness.length; i++){
                genFitness[i]/=best;
            }
        }
        
        //now to graph...
        DefaultCategoryDataset dataSet;
        dataSet = new DefaultCategoryDataset();
        String legend = "individuals";
        for (int i = 0; i < genMax; i++) {
            dataSet.setValue((Number) genFitness[i], legend, i+1);
        }
        JFreeChart chart = ChartFactory.createBarChart("bestRelativeFitness", "Generations", "Fitness", dataSet);
        String prevPath=f.getPath();
        String save = prevPath.substring(0,prevPath.lastIndexOf("\\"))+"\\chart.jpg";
        try {
            out=new File(save);
            ChartUtilities.saveChartAsJPEG(out , chart, 500, 500);
        } catch (IOException e) {
            out=null;
            System.err.print("Failed to save chart\n");
        }
        return out;
    }
    //------------------------------------------------------------------------------------------------------------------------------------
    
    
    /**
     * Prints data on the individual generation selected.
     * @param f
     * @return 
     */
    public File botLoad(File f){
        File out;
        Generation G;
        int individuals, generationNumber;
        int[] sensorTimes= new int[8];
        int[][] genetics= new int[2][10];
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(f));
            generationNumber = in.readInt();
            individuals = in.readInt();
            G = new Generation(individuals, generationNumber);
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
                G.addIndividual(individual);
            }
            in.close();

        } catch (IOException e) {
            System.out.println("Failed to read from: "+f.getAbsolutePath());
            individuals=0;
            generationNumber=-1;
            G=null;
        }
        
        
        
        //now to graph...
        DefaultCategoryDataset dataSet;
        dataSet = new DefaultCategoryDataset();
        String legend = "individuals";
        for (int i = 0; i < individuals; i++) {
            dataSet.setValue((Number) G.getIndividual(i).getFitness(), legend, G.getIndividual(i).getNumber());
        }
        JFreeChart chart = ChartFactory.createBarChart("Generation "+generationNumber, "individual", "Fitness", dataSet);
        String prevPath=f.getPath();
        String save = prevPath.substring(0,prevPath.lastIndexOf("\\"))+"\\chartGen"+generationNumber+".jpg";
        try {
            out=new File(save);
            ChartUtilities.saveChartAsJPEG(out , chart, 500, 500);
        } catch (IOException e) {
            out=null;
            System.err.print("Failed to save chart\n");
        }
        return out;
    }
}
