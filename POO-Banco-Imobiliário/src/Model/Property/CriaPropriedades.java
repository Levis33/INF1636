package Model.Property;
import java.awt.Color;

public class CriaPropriedades {
    public static final Color purple = new Color(102,0,153);
    public static Property[] cria() {

        String[] name = { // ordem do tabuleiro
            "Leblon","Av. Presidente Vargas","Av. Nossa S. de Copacabana", "Companhia Ferroviaria","Av. Brig. Faria Lima", "Companhia de Onibus", "Av. Rebouças", "Av. 9 de Julho","Av. Europa", "Rua Augusta", "Av. Pacaembu", "Companhia de Taxi","Interlagos", "Morumbi","Flamengo", "Botafogo", "Companhia de Navegacao","Av. Brasil", "Av. Paulista", "Jardim Europa","Copacabana", "Companhia de Aviacao", "Av. Vieira Souto", "Av. Atlantica", "Companhia de Helicoptero", "Ipanema","Jardim Paulista", "Brooklin"
        }; 

        int [][] price = {
            {100, 50} , {60, 50}  , {60, 50}  , {200},{240, 150}, {200}     , {220, 150}, {220, 150},{220, 100}, {310, 100}, {310, 100}, {150},{350, 200}, {400, 200},{120, 50} , {100, 50} , {150},{160, 100}, {140, 100}, {140, 100},{260, 150}, {200}     , {320, 200}, {300, 200}, {200}, {300, 200},{280, 150}, {260, 150}
        };

        int [][] rent = {
            {6,30,90,270,400,500}, {2,10,30,90,160,250}, {4,20,60,180,320,450}, {50},{20,100,300,750,925,1100}, {50}, {18,90,250,700,875,1050}, {18,90,250,700,875,1050},{16,80,220,600,800,1000}, {14,70,200,550,750,950}, {14,70,200,550,750,950}, {40},{35,175,500,1100,1300,1500}, {50,200,600,1400,1700,2000},{8,40,100,300,450,600}, {6,30,90,270,400,500}, {40},{12,60,180,500,700,900}, {10,50,150,450,625,750}, {10,50,150,450,625,750},{22,110,330,800,975,1150}, {50}, {28,150,450,1000,1200,1400}, {26,130,390,900,1100,1275}, {50}, {26,130,390,900,1100,1275},{24,120,360,850,1025,1200}, {22,110,330,800,975,1150}
        };

        Color [] color = {
            Color.pink,Color.pink,Color.pink,Color.blue,Color.blue,Color.blue,Color.magenta,Color.magenta,Color.magenta,Color.orange,Color.orange,Color.red,Color.red,Color.yellow,Color.yellow,Color.yellow,Color.green,Color.green,Color.green,Color.green,purple,purple,
        };

        Property[] properties = new Property[28];
        int i;
        int inserted_enterprises = 0;
        for(i = 0; i<28; i++){
            
            if(i == 3 || i == 5 || i == 11 || i == 16 || i == 21 || i == 24){
                properties[i] = new Enterprise(name[i], rent[i], price[i][0]);
                inserted_enterprises += 1;
            }
            else{
                properties[i] = new Ground(name[i], rent[i], price[i], color[i-inserted_enterprises]);
            }
        }
        return properties;
    }
}
