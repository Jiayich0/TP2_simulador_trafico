package simulator.view;

import java.awt.Color;

public class MyColors {
	/**
	 * Opciones disponibles:
	 * 		- default	[gris-azul]			->	null | " " | "" | string no válido
	 * 		- rosa		[tonos rosa]		->	"rosa"
	 * 		- verde 	[verde-marrón]		->	"verde"
	 * 		- hoppip  [paleta del pokemon]	->	"hoppip"
	 * 		- choco		[marrón]			->	"choco"
	 * 		- verde2	[verde-azul]		->	"verdeAzul"
	 */
	private static final String _option = " ";
	
	public static final Color RED = Color.RED;
	public static final Color GREEN = Color.GREEN;
	public static final Color GRAY = Color.GRAY;
	public static final Color BLACK = Color.BLACK;
	public static final Color JUNCTION_COLOR = Color.BLUE;
	public static final Color JUNCTION_LABEL_COLOR = new Color(200, 100, 0);
	
	public static Color ELEMENTOS = new Color(0x8ca4f4);		// #7289da azul Discord un poco más claro
    public static Color TEXTO = Color.WHITE;                 	// #ffffff
    public static Color FONDO1 = new Color(0xe6ebfc); 			// #c9d4dc azul/gris claro para fondos
    public static Color FONDO2 = new Color(0x99aab5);			// #99aab5 gris oscuro Discord
    
    static {
    	changePalette(_option);
    }
    
    private static void changePalette(String option) {
    	switch(option) {
    		case "rosa":
    			ELEMENTOS = new Color(0xe021d8);
    		    FONDO1 = new Color(0xffc0ed);
    		    FONDO2 = new Color(0xf658b8);
    		    break;
    		case "verde":
    			ELEMENTOS = new Color(0x7c390c);
    		    FONDO1 = new Color(0xe0fdd6);
    		    FONDO2 = new Color(0x7fec56);
    			break;
    		case "hoppip":
    			ELEMENTOS = new Color(0x669900);
    		    FONDO1 = new Color(0xffc0d);
    		    FONDO2 = new Color(0xff1ac6);
    			break;
    		case "choco":
    			ELEMENTOS = new Color(0x461904);
    		    FONDO1 = new Color(0xfce3a1);
    		    FONDO2 = new Color(0x6c3822);
    			break;
    		case "verde2":
    			ELEMENTOS = new Color(0x09345a);
    		    FONDO1 = new Color(0x86d562);
    		    FONDO2 = new Color(0x9aa0a4);
    			break;
    	}
    }
}