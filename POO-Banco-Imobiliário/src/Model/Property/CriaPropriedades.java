package Model.Property;

import java.awt.Color;

public class CriaPropriedades {
    public static final Color purple = new Color(102, 0, 153);

    public static Property[] cria() {

        String[] name = { // ordem do tabuleiro
                "Partida", "Leblon", "Sorte/Reves", "Av. Presidente Vargas", "Av. Nossa S. de Copacabana",
                "Companhia Ferroviaria", "Av. Brigadeiro Faria Lima", "Companhia de Onibus", "Av. Rebouças",
                "Av. 9 de Julho", "Prisao",
                "Av. Europa", "Sorte/Reves", "Rua Augusta", "Av. Pacaembu", "Companhia de Taxi",
                "Sorte/Reves", "Interlagos", "Ganhe", "Morumbi", "Parada Livre",
                "Flamengo", "Sorte/Reves", "Botafogo", "Imposto", "Companhia de Navegacao",
                "Av. Brasil", "Sorte/Reves", "Av. Paulista", "Jardim Europa", "Va para a prisao",
                "Copacabana", "Companhia de Aviacao", "Av. Vieira Souto", "Av. Atlantica", "Companhia de Helicoptero",
                "Ipanema", "Sorte/Reves", "Jardim Paulista", "Brooklin"
        };

        int[][] price = {
                { 100, 50 }, { 60, 50 }, { 60, 50 },
                { 200 }, { 240, 150 }, { 200 }, { 220, 150 }, { 220, 150 },
                { 220, 100 }, { 310, 100 }, { 310, 100 }, { 150 },
                { 350, 200 }, { 400, 200 },
                { 120, 50 }, { 100, 50 }, { 150 },
                { 160, 100 }, { 140, 100 }, { 140, 100 },
                { 260, 150 }, { 200 }, { 320, 200 }, { 300, 200 }, { 200 },
                { 300, 200 }, { 280, 150 }, { 260, 150 }
        };

        int[][] rent = {
                { 6, 30, 90, 270, 400, 500 }, { 2, 10, 30, 90, 160, 250 }, { 4, 20, 60, 180, 320, 450 },
                { 50 }, { 20, 100, 300, 750, 925, 1100 }, { 50 }, { 18, 90, 250, 700, 875, 1050 },
                { 18, 90, 250, 700, 875, 1050 },
                { 16, 80, 220, 600, 800, 1000 }, { 14, 70, 200, 550, 750, 950 }, { 14, 70, 200, 550, 750, 950 },
                { 40 },
                { 35, 175, 500, 1100, 1300, 1500 }, { 50, 200, 600, 1400, 1700, 2000 },
                { 8, 40, 100, 300, 450, 600 }, { 6, 30, 90, 270, 400, 500 }, { 40 },
                { 12, 60, 180, 500, 700, 900 }, { 10, 50, 150, 450, 625, 750 }, { 10, 50, 150, 450, 625, 750 },

                { 22, 110, 330, 800, 975, 1150 },
                { 50 }, { 28, 150, 450, 1000, 1200, 1400 }, { 26, 130, 390, 900, 1100, 1275 }, { 50 },
                { 26, 130, 390, 900, 1100, 1275 },
                { 24, 120, 360, 850, 1025, 1200 }, { 22, 110, 330, 800, 975, 1150 }
        };

        int[][] pos = {
                { 638, 600 },
                { 553, 630 },
                { 493, 630 },
                { 443, 630 },
                { 383, 630 },
                { 330, 630 },
                { 275, 630 },
                { 220, 630 },
                { 165, 630 },
                { 110, 630 },
                { 20, 600 },
                
                { 15, 535 },
                { 15, 482 },
                { 15, 430 },
                { 15, 377 },
                { 15, 325 },
                { 15, 272 },
                { 15, 220 }, 
                { 15, 167 }, 
                { 15, 115 },
                
                { 15, 50 }, 
                { 105, 50 },
                { 160, 20 }, 
                { 215, 50 },
                { 270, 20 },
                { 325, 20 }, 
                { 380, 50 }, 
                { 435, 20 }, 
                { 490, 50 }, 
                { 545, 50 },
                { 630, 50 },

                { 630, 115 },
                { 650, 167 },
                { 630, 220 },
                { 630, 272 },
                { 650, 325 },
                { 630, 377 },
                { 650, 430 },
                { 630, 482 },
                { 630, 535 },
             };

        Color[] color = {
                Color.pink, Color.pink, Color.pink, Color.blue, Color.blue, Color.blue, Color.magenta, Color.magenta,
                Color.magenta, Color.orange, Color.orange, Color.red, Color.red, Color.yellow, Color.yellow,
                Color.yellow, Color.green, Color.green, Color.green, Color.green, purple, purple,
        };

        Property[] properties = new Property[40];
        int i;
        int inserted_fields = 0;
        int fieldsWithoutColor = 0;
        for (i = 0; i < 40; i++) {
            if (i == 0 || i == 10 || i == 20 || i == 30) {
                properties[i] = new SpecialProperty(name[i], pos[i][0], pos[i][1]);
                inserted_fields += 1;
                fieldsWithoutColor += 1;
            } else if (i == 2 || i == 12 || i == 16 || i == 18 || i == 22 || i == 24 || i == 27 || i == 37) {
                properties[i] = new SpecialProperty(name[i], pos[i][0], pos[i][1]);
                inserted_fields += 1;
                fieldsWithoutColor += 1;
            } else if (i == 5 || i == 7 || i == 15 || i == 25 || i == 32 || i == 35) {
                properties[i] = new Enterprise(name[i], rent[i - inserted_fields], price[i - inserted_fields][0],
                        pos[i][0], pos[i][1], i - inserted_fields);
                fieldsWithoutColor += 1;
            } else {
                properties[i] = new Ground(name[i], rent[i - inserted_fields], price[i - inserted_fields], pos[i][0],
                        pos[i][1],
                        color[i - fieldsWithoutColor], i-inserted_fields);
            }
        }
        return properties;
    }
}
