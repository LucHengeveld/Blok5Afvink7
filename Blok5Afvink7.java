/**
 * Naam: Luc Hengeveld
 * Klas: BIN-2d
 * Studentnummer: 627071
 * Datum: 30-10-2020
 */

package Afvink7;

// Alle imports
import javax.swing.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;


public class Blok5Afvink7 extends JFrame implements ActionListener {


    // Aantal gegevens aanmaken
    public JTextField inputtextfield;
    public JTextArea outputtextarea;
    public JButton blader, visualiseer;
    public JPanel panel;
    public JFrame frame;
    public Color kleur;
    public int counter;
    boolean dnaboolean = false, rnaboolean = false, aaboolean = false, onjuisteinvoer = false;
    ArrayList<Color> KleurArray = new ArrayList<>();
    static final String[] dna = {"A", "C", "T", "G"};
    static final String[] rna = {"A", "C", "U", "G"};
    static final String[] aa = {"A", "R", "N", "D", "C", "Q", "E", "G", "H", "I", "L",
            "K", "M", "F", "P", "S", "T", "W", "Y", "V"};


    /**
     * Main functie
     * Maakt het frame aan en roept createGUI aan
     */
    public static void main(String[] args) {
        Blok5Afvink7 frame = new Blok5Afvink7();
        frame.createGUI();
    }


    /**
     * Maken van de GUI
     */
    private void createGUI() {
        // Maakt het frame en panel aan
        frame = new JFrame("Afvink 7 Blok 5");
        panel = new JPanel();
        frame.getContentPane();

        //Maken van een label
        JLabel infolabel = new JLabel("Geef een bestand met DNA of peptides om te visualiseren");
        infolabel.setBounds(20, 20, 400, 25);

        //Maken van een label
        JLabel bestandlabel = new JLabel("Bestand");
        bestandlabel.setBounds(20, 55, 80, 25);

        //Plaatsen van een een textfield voor de input
        inputtextfield = new JTextField("");
        inputtextfield.setBounds(120, 55, 290, 25);

        //Maken van een button en actionlistener koppelen
        blader = new JButton("Blader");
        blader.setBounds(420, 55, 100, 25);
        blader.addActionListener(this);

        //Plaatsen van een label
        JLabel seqlabel = new JLabel("Sequentie");
        seqlabel.setBounds(20, 90, 80, 25);

        //Plaatsen van een een textarea voor de input (voor meerdere regels te krijgen als output)
        outputtextarea = new JTextArea("");
        outputtextarea.setBounds(120, 90, 400, 250);
        outputtextarea.setLineWrap(true);

        //Maken van een button en actionlistener koppelen
        visualiseer = new JButton("Visualiseer");
        visualiseer.setBounds(120, 355, 100, 25);
        visualiseer.addActionListener(this);

        //Toevoegen alle gegevens aan de JFrame
        panel.setLayout(null);
        panel.add(infolabel);
        panel.add(bestandlabel);
        panel.add(inputtextfield);
        panel.add(blader);
        panel.add(visualiseer);
        panel.add(seqlabel);
        panel.add(outputtextarea);

        //Zorgt ervoor dat de gui niet gelijk sluit, voegt panel toe aan frame, en set de grootte van de frame
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setSize(560, 500);
        frame.setVisible(true);
    }


    /**
     * Word geactiveerd als er op een button geklikt is. Er wordt gekeken op welke button er is geklikt en hierna
     * worden een aantal stappen uitgevoerd.
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        //Kijkt of er op blader is geklikt. Zo ja dan opent er een file chooser en word het ingevoerde
        // bestand opgeslagen
        if (event.getSource() == blader) {
            JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

            int returnValue = fc.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fc.getSelectedFile();
                String bestandslocatie = selectedFile.getAbsolutePath();

                inputtextfield.setText(bestandslocatie);
            }
        }

        //Kijkt of er op visualiseer is geklikt. Zo ja dan worden de stappen hieronder uitgevoerd
        if (event.getSource() == visualiseer) {
            //Maakt een stringbuilder aan en slaat de bestandslocatie op
            StringBuilder sequentie = new StringBuilder();
            String bestandslocatie = inputtextfield.getText();

            //Kijkt of het bestand bestaat
            File bestand = new File(bestandslocatie);
            Scanner bestandlezer = null;
            try {
                bestandlezer = new Scanner(bestand);
            } catch (FileNotFoundException e) {
                outputtextarea.setText("Bestand is niet gevonden. Voer een nieuw bestand in.");
            }

            //Als het bestand bestaat komt het in de while loop. Het bestand word ingelezen en de tekst opgeslagen
            while (true) {
                assert bestandlezer != null;
                if (!bestandlezer.hasNextLine()) break;
                String data = bestandlezer.nextLine();
                sequentie.append(data);

                //Er word gegeken of de sequentie DNA is
                String sequentieString = sequentie.toString().toUpperCase();
                StringBuilder seq = new StringBuilder();
                for (int i = 0; i < sequentie.length(); i++) {
                    char letterchar = sequentie.charAt(i);
                    String letterstring = Character.toString(letterchar);
                    for (int j = 0; j < dna.length; j++) {
                        if (dna[j].equals(letterstring)) {
                            seq.append(dna[j]);
                        }
                    }
                }
                if (seq.length() == sequentie.length()) {
                    counter = 0;
                    dnaboolean = true;
                } else {
                    counter = 1;
                    seq = new StringBuilder();
                }

                //Er word gegeken of de sequentie RNA is als het geen DNA is
                if (counter != 0) {
                    for (int i = 0; i < sequentie.length(); i++) {
                        char letterchar = sequentie.charAt(i);
                        String letterstring = Character.toString(letterchar);
                        for (int j = 0; j < rna.length; j++) {
                            if (rna[j].equals(letterstring)) {
                                seq.append(rna[j]);
                            }
                        }
                    }
                    if (seq.length() == sequentie.length()) {
                        counter = 0;
                        rnaboolean = true;
                    } else {
                        counter = 1;
                        seq = new StringBuilder();
                    }
                }

                //Er word gegeken of de sequentie een peptide is als het geen RNA of DNA is
                if (counter != 0) {
                    for (int i = 0; i < sequentie.length(); i++) {
                        char letterchar = sequentie.charAt(i);
                        String letterstring = Character.toString(letterchar);
                        for (int j = 0; j < aa.length; j++) {
                            if (aa[j].equals(letterstring)) {
                                seq.append(aa[j]);
                            }
                        }
                    }
                    if (seq.length() == sequentie.length()) {
                        counter = 0;
                        aaboolean = true;
                    } else {
                        counter = 1;
                        seq = new StringBuilder();
                    }
                }

                //Als de sequentie geen DNA, RNA of peptide is word de boolean onjuisteinvoer true
                if (counter != 0) {
                    onjuisteinvoer = true;
                }

                //Vraagt de graphics van de panel op
                Graphics paper = panel.getGraphics();

                //Berekend de lengte van de sequentie, breedte van elke letter vergeleken met de breedte van de hele
                //balk en geeft de X coördinaat mee
                int LenSeq = sequentie.length();
                int BreedtePerLetter = (500 / LenSeq);
                int X = 20;

                //Maakt de KleurArray leeg
                KleurArray.clear();

                //Als de sequentie DNA is komt de ingevoerde sequentie in de textarea te staan en worden de kleuren van
                //de visualisatie balk berekend.
                if (dnaboolean) {
                    outputtextarea.setText("De ingevoerde sequentie is DNA \n" + sequentieString);

                    for (int i = 0; i < sequentie.length(); i++) {
                        char letterchar = sequentie.charAt(i);
                        String letterstring = Character.toString(letterchar);
                        //GC Rood - AT Geel
                        //Kijkt wat elke letter is en voegt hier een kleur bij toe aan de kleurarray
                        if (letterstring.equals("G") || letterstring.equals("C")) {
                            kleur = Color.RED;
                        } else {
                            kleur = Color.YELLOW;
                        }
                        KleurArray.add(kleur);
                    }
                    //Maakt de gekleurde visualisatie balk aan
                    for (int i = 0; i < LenSeq; i++) {
                        paper.setColor(KleurArray.get(i));
                        paper.fillRect(X, 390, BreedtePerLetter, 50);
                        X += BreedtePerLetter;
                    }
                }

                if (rnaboolean) {
                    outputtextarea.setText("De ingevoerde sequentie is RNA \n" + sequentieString);

                    for (int i = 0; i < sequentie.length(); i++) {
                        char letterchar = sequentie.charAt(i);
                        String letterstring = Character.toString(letterchar);
                        // GC Rood - AT Geel
                        //Kijkt wat elke letter is en voegt hier een kleur bij toe aan de kleurarray
                        if (letterstring.equals("G") || letterstring.equals("C")) {
                            kleur = Color.RED;
                        } else {
                            kleur = Color.YELLOW;
                        }
                        KleurArray.add(kleur);
                    }
                    //Maakt de gekleurde visualisatie balk aan
                    for (int i = 0; i < LenSeq; i++) {
                        paper.setColor(KleurArray.get(i));
                        paper.fillRect(X, 390, BreedtePerLetter, 50);
                        X += BreedtePerLetter;
                    }
                }

                if (aaboolean) {
                    outputtextarea.setText("De ingevoerde sequentie is een aminozuursequentie \n" + sequentieString);

                    for (int i = 0; i < sequentie.length(); i++) {
                        char letterchar = sequentie.charAt(i);
                        String letterstring = Character.toString(letterchar);
                        //polair blauw - apolair rood - neutraal grijs
                        //polair = {"N", "C", "Q", "S", "T", "Y"};
                        //apolair = {"A", "I", "L", "M", "F", "P", "W", "V", "G"};
                        //neutraal = {"D", "E", "R", "K", "H"};
                        //Kijkt wat elke letter is en voegt hier een kleur bij toe aan de kleurarray
                        if (letterstring.equals("N") || letterstring.equals("C") || letterstring.equals("Q") ||
                                letterstring.equals("S") || letterstring.equals("T") || letterstring.equals("Y")) {
                            kleur = Color.BLUE;
                        } else if (letterstring.equals("A") || letterstring.equals("I") || letterstring.equals("L") ||
                                letterstring.equals("M") || letterstring.equals("F") || letterstring.equals("P")
                                || letterstring.equals("W") || letterstring.equals("V") || letterstring.equals("G")) {
                            kleur = Color.RED;
                        } else {
                            kleur = Color.GRAY;
                        }
                        KleurArray.add(kleur);
                    }
                    //Maakt de gekleurde visualisatie balk aan
                    for (int i = 0; i < LenSeq; i++) {
                        paper.setColor(KleurArray.get(i));
                        paper.fillRect(X, 390, BreedtePerLetter, 50);
                        X += BreedtePerLetter;
                    }
                }

                //Maakt de visualisatie balk wit voor het geval dat hiervoor een andere sequentie is gevisualiseerd.
                if (onjuisteinvoer) {
                    outputtextarea.setText("De ingevoerde sequentie is onjuist \n" + sequentieString);
                    paper.setColor(Color.WHITE);
                    paper.fillRect(20, 390, 500, 50);
                }
            }
        }
    }
}