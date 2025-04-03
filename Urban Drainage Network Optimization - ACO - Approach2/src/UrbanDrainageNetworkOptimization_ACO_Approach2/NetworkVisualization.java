package UrbanDrainageNetworkOptimization_ACO_Approach2;
import javax.swing.*;
import java.awt.*;
import java.util.List;
public class NetworkVisualization extends JPanel {
    private DrainageNetwork network;
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private static final int PADDING = 50;
    private static final int NODE_SIZE = 12;
    public NetworkVisualization(DrainageNetwork network) {
        this.network = network;
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setBackground(Color.WHITE);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        double minX = Double.MAX_VALUE, maxX = Double.MIN_VALUE;
        double minY = Double.MAX_VALUE, maxY = Double.MIN_VALUE;
        for (DrainageNode node : network.getNodes()) {
            minX = Math.min(minX, node.getX());
            maxX = Math.max(maxX, node.getX());
            minY = Math.min(minY, node.getY());
            maxY = Math.max(maxY, node.getY());
        }
        double width = maxX - minX == 0 ? 1 : maxX - minX;
        double height = maxY - minY == 0 ? 1 : maxY - minY;
        double scale = Math.min((WINDOW_WIDTH - 2 * PADDING) / width, (WINDOW_HEIGHT - 2 * PADDING) / height);
        g2d.setColor(Color.LIGHT_GRAY);
        for (int x = 0; x <= width; x += 50) {
            int screenX = toScreenX(minX + x, minX, scale);
            g2d.drawLine(screenX, PADDING, screenX, WINDOW_HEIGHT - PADDING);
        }
        for (int y = 0; y <= height; y += 50) {
            int screenY = toScreenY(minY + y, minY, scale);
            g2d.drawLine(PADDING, screenY, WINDOW_WIDTH - PADDING, screenY);
        }
        g2d.setColor(Color.BLUE);
        g2d.setStroke(new BasicStroke(2));
        for (DrainagePipe pipe : network.getPipes()) {
            int x1 = toScreenX(pipe.getStartNode().getX(), minX, scale);
            int y1 = toScreenY(pipe.getStartNode().getY(), minY, scale);
            int x2 = toScreenX(pipe.getEndNode().getX(), minX, scale);
            int y2 = toScreenY(pipe.getEndNode().getY(), minY, scale);
            g2d.drawLine(x1, y1, x2, y2);
            String label = String.format("%.2f m³/s (D=%.1fm)", pipe.getFlowCapacity(), pipe.getDiameter());
            g2d.setColor(Color.BLACK);
            g2d.drawString(label, (x1 + x2) / 2 + 5, (y1 + y2) / 2 - 5);
            g2d.setColor(Color.BLUE);
        }
        g2d.setColor(Color.RED);
        for (DrainageNode node : network.getNodes()) {
            int x = toScreenX(node.getX(), minX, scale);
            int y = toScreenY(node.getY(), minY, scale);
            g2d.fillOval(x - NODE_SIZE / 2, y - NODE_SIZE / 2, NODE_SIZE, NODE_SIZE);
            g2d.setColor(Color.BLACK);
            g2d.drawString("N" + node.getId() + " (" + node.getX() + "," + node.getY() + ")", 
                           x + NODE_SIZE / 2 + 2, y - NODE_SIZE / 2);
            g2d.setColor(Color.RED);
        }
        drawLegend(g2d);
    }
    private int toScreenX(double x, double minX, double scale) {
        return (int) (PADDING + (x - minX) * scale);
    }
    private int toScreenY(double y, double minY, double scale) {
        double scaledY = (y - minY) * scale;
        return (int) (WINDOW_HEIGHT - PADDING - scaledY);
    }
    private void drawLegend(Graphics2D g2d) {
        int legendX = PADDING;
        int legendY = WINDOW_HEIGHT - PADDING + 20;
        g2d.setColor(Color.BLACK);
        g2d.drawString("Legend:", legendX, legendY - 20);
        g2d.setColor(Color.RED);
        g2d.fillOval(legendX, legendY - 15, NODE_SIZE, NODE_SIZE);
        g2d.setColor(Color.BLACK);
        g2d.drawString("= Nodes (ID, X,Y)", legendX + NODE_SIZE + 5, legendY - 10);
        g2d.setColor(Color.BLUE);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(legendX, legendY + 5, legendX + 20, legendY + 5);
        g2d.setColor(Color.BLACK);
        g2d.drawString("= Pipes (Flow Capacity, Diameter)", legendX + 25, legendY + 10);
    }
    public static void display(DrainageNetwork network) {
        JFrame frame = new JFrame("Optimized Urban Drainage Network Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new NetworkVisualization(network));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}