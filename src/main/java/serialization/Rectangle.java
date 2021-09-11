package serialization;

public class Rectangle {

    private final double height;
    private final double width;

    public Rectangle(double height, double width)
    {
        this.height = height;
        this.width = width;
    }

    public double Area()
    {
        return height * width;
    }

    public double Perimeter()
    {
        return 2 * (height + width);
    }
}
