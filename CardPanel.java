import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


class CardPanel extends JPanel {
        private String shape; 
        private boolean flipped = false;
        private Image backDesign; 

        public CardPanel(String shape) {
            this.shape = shape;
            backDesign = new ImageIcon("card.png").getImage(); // wanted to incorporate an image of a real playing card as the design
        }

        public void setShape(String shape) {
            this.shape = shape;
        }

        public void flip() {
            flipped = true;
            repaint();
        }

        public void unflip() {
            flipped = false;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (!flipped) { // if not flipped, the card will look like a blue playing card 
                g.drawImage(backDesign, 0, 0, getWidth(), getHeight(), this);
            }
            else {
                g.setColor(Color.WHITE);    // sets the background of the card to white so it can draw on top 
                g.fillRect(0,0,getWidth(),getHeight()); 
                
                if (shape.equals("circle")) {   // will draw a shape based on the card type 
                    g.setColor(new Color(0x123456));
                    g.fillOval(40,60,80,80);
                }
                else if (shape.equals("square")) {
                    g.setColor(new Color(0x123456));
                    g.fillRect(40,60,70,70);
                }
                else if (shape.equals("triangle")) {
                    g.setColor(new Color(0x123456));
                    int[] xPoints = {82, 120, 44};
                    int[] yPoints = {60, 140, 140};
                    g.fillPolygon(xPoints, yPoints, 3);
                }
            }
    }
}
