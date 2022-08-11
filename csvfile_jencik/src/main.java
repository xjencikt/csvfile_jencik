import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class main {
    public static void main(String[] args) throws IOException {
        String file = "C:\\Users\\tomas\\Desktop\\csvfile_jencik\\podkladoveData.csv"; //-----pre spustenie treba zadat miesto daneho csv suboru
        BufferedReader reader = null;
        FileWriter writer = new FileWriter("doplneneData.csv");
        String line = "";
        int num_row=0;
        int num_col=0;

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String str = formatter.format(date);
        //System.out.print("Current date: "+str); //-------aktualny datum



        try {
            reader  = new BufferedReader(new FileReader(file));
            while((line = reader.readLine()) !=null) {
                String[] row = line.split(";"); //--------rozdelenie riadku

                if (row.length<7) {
                    row = Arrays.copyOf(row, row.length + 3);
                }


                for (String index : row) {


                    if (num_row>0){
                        num_col=num_col+1;
                        try {
                            if (num_col == 4) {
                                if (Integer.parseInt(row[1]) + Integer.parseInt(row[2]) == Integer.parseInt(row[3])) {
                                    row[4] = "OK";
                                    row[5] = "NO ERROR";
                                } else { //----------------- spocitavanie kanalov pre kontrolu
                                    row[4] = "NOK";
                                    row[5] = "NO ERROR";
                                }
                                num_col = 0;
                            }

                            SimpleDateFormat sdformat = new SimpleDateFormat("dd.MM.yyyy");
                            Date d1 = sdformat.parse(row[0]);
                            Date d2 = sdformat.parse(str);
                            if(d1.compareTo(d2) > 0) { //------------- porovnavanie pre cas.ramec
                                row[6] = "PREDIKCIA";
                            } else if(d1.compareTo(d2) < 0) {
                                row[6] = "DONE";
                            } else if(d1.compareTo(d2) == 0) {
                                row[6] = "ROVNAKE";
                            }


                        }
                        catch(Exception f){ //----------najde chybu a napise do poznamky
                            row[4] = "CHYBA";
                            row[5] = String.valueOf(f);
                            num_col = 0;
                        }
                    }

                    System.out.printf("%-10s",index); //--------- vypisanie na konzolu, nie potrebne
                    writer.write(index);
                    writer.append(";");



                }
                num_row=num_row+1;
                writer.append('\n');
                System.out.println();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally{
            reader.close();
            writer.close();
        }
    }
}
