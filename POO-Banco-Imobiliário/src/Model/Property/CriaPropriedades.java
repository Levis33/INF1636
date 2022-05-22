package Model.Property;
import java.awt.Color;

public class CriaPropriedades {
    public static final Color purple = new Color(102,0,153);
    public static Property[] cria() {

        String[] name_ground = {
            "Leblon",	
            "Av. Presidente Vargas",
            "Av. Nossa Sra. De Copacabana",	
            "Av. Brigadeiro Faria Lima",	
            "Av. Rebouças",
            "Av. 9 de Julho",		
            "Av. Europa",	
            "Rua Augusta",
            "Av. Pacaembú",	
            "Interlagos",		
            "Morumbi",		
            "Flamengo",	
            "Botafogo",
            "Av. Brasil",	
            "Av. Paulista",	
            "Jardim Europa",		
            "Copacabana",	
            "Av. Vieira Souto",	
            "Av. Atlântica",	
            "Ipanema",	
            "Jardim Paulista",	
            "Brooklin",
        };

        String[] name_enterprise = {
            "Companhia Ferroviária",
            "Companhia de Viação",
            "Companhia de Táxi",
            "Companhia de Navegação",
            "Companhia de Aviação",
            "Companhia de Táxi Aéreo",
        };

        int [][] price_ground = {
            {100, 50},
            {60, 50},
            {60, 50},
            {240, 150},
            {220, 150},
            {220, 150},
            {220, 100},
            {310, 100},
            {310, 100},
            {350, 200},
            {400, 200},
            {120, 50},
            {100, 50},
            {160, 100},
            {140, 100},
            {140, 100},
            {260, 150},
            {320, 200},
            {300, 200},
            {300, 200},
            {280, 150},
            {260, 150},
        };

        int [] price_enterprise = {
            200,
            200,
            150,
            150,
            200,
            200,
        };

        int [][] rent_ground = {
            {6,30,90,270,400,500},
            {2,10,30,90,160,250},
            {4,20,60,180,320,450},
			{20,100,300,750,925,1100},
            {18,90,250,700,875,1050},
            {18,90,250,700,875,1050},
			{16,80,220,600,800,1000},
            {14,70,200,550,750,950},
            {14,70,200,550,750,950},
			{35,175,500,1100,1300,1500},
            {50,200,600,1400,1700,2000},
			{8,40,100,300,450,600},
            {6,30,90,270,400,500},
			{12,60,180,500,700,900},
            {10,50,150,450,625,750},
            {10,50,150,450,625,750},
			{22,110,330,800,975,1150},
            {28,150,450,1000,1200,1400},
            {26,130,390,900,1100,1275},
            {26,130,390,900,1100,1275},
			{24,120,360,850,1025,1200},
            {22,110,330,800,975,1150}
        };

        int [][] rent_enterprise = {
            {50},
            {50},
            {40},
            {40},
            {50},
            {50},
        };

        Color [] color_ground = {
            Color.pink,
            Color.pink,
            Color.pink,
            Color.blue,
            Color.blue,
            Color.blue,
            Color.magenta,
            Color.magenta,
            Color.magenta,
            Color.orange,
            Color.orange,
            Color.red,
            Color.red,
            Color.yellow,
            Color.yellow,
            Color.yellow,
            Color.green,
            Color.green,
            Color.green,
            Color.green,
            purple,
            purple,

        };

        Property[] properties = new Property[28];
        int i;
        for(i = 0; i<28; i++){
            if(i > 21){
                properties[i] = new Ground(name_ground[i], rent_ground[i], price_ground[i], color_ground[i]);

            }
            else{
                properties[i] = new Enterprise(name_enterprise[i], rent_enterprise[i], price_enterprise[i]);
            }
        }
        return properties;
    }
}
