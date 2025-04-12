package simulator.view;

import java.awt.Color;

/*public class MyColors  {
	
	public static final Color ELEMENTOS = new Color(0x8c, 0xa4, 0xf4);  // #7289da azul Discord un poco más claro
	public static final Color TEXTO = Color.WHITE;                 		// #ffffff
	public static final Color FONDO1 = new Color(0xc9, 0xd4, 0xdc); 	// #c9d4dc gris claro para fondos
	public static final Color FONDO2 = new Color(0x99, 0xaa, 0xb5);		// #99aab5 gris oscuro Discord

    private MyColors() {}
}*/

public class MyColors  {
	
	private static final String _option = "verde";
	
	public static Color ELEMENTOS = new Color(0x8c, 0xa4, 0xf4);	// #7289da azul Discord un poco más claro
    public static Color TEXTO = Color.WHITE;                 		// #ffffff
    public static Color FONDO1 = new Color(0xe6, 0xeb, 0xfc); 		// #c9d4dc azul/gris claro para fondos
    public static Color FONDO2 = new Color(0x99, 0xaa, 0xb5);		// #99aab5 gris oscuro Discord

    static {
    	changePalette(_option);
    }
    
    private static void changePalette(String option) {
    	switch(option) {
    		case "rosa":
    			ELEMENTOS = new Color(0xe0, 0x21, 0xd8);
    			TEXTO = Color.WHITE;
    		    FONDO1 = new Color(0xff, 0xc0, 0xed);
    		    FONDO2 = new Color(0xf6, 0x58, 0xb8);
    		    break;
    		case "verde":
    			ELEMENTOS = new Color(0x7c, 0x39, 0x0c);
    			TEXTO = Color.WHITE;
    		    FONDO1 = new Color(0xe0, 0xfd, 0xd6);
    		    FONDO2 = new Color(0x7f, 0xec, 0x56);
    			break;
    		case "hoppip":
    			ELEMENTOS = new Color(0x66, 0x99, 0x00);
    			TEXTO = Color.WHITE;
    		    FONDO1 = new Color(0xff, 0xc0, 0xed);
    		    FONDO2 = new Color(0xff, 0x1a, 0xc6);
    			break;
    	}
    }
}
	

//public static final Color ELEMENTOS; = new Color(0x8c, 0xa4, 0xf4);  // #7289da azul Discord un poco más claro
//public static final Color TEXTO = Color.WHITE;                 		// #ffffff
//public static final Color FONDO1 = new Color(0xc9, 0xd4, 0xdc); 	// #c9d4dc gris claro para fondos
//public static final Color FONDO2 = new Color(0x99, 0xaa, 0xb5);		// #99aab5 gris oscuro Discord

/*public class MyColors  {
	
	public static final Color ELEMENTOS = new Color(0xe0, 0x21, 0xd8);
    public static final Color TEXTO = Color.WHITE;
    public static final Color FONDO1 = new Color(0xff, 0xc0, 0xed);
    public static final Color FONDO2 = new Color(0xf6, 0x58, 0xb8);

    private MyColors() {}
}*/