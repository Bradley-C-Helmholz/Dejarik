
public class GFG
{

    // Function to convert degree to radian
    static double ConvertDegToRad(double degree)
    {
        double pi = 3.14159;
        return (degree * (pi / 180.0));
    }

    // Function to convert the polar
// coordinate to cartesian
    static double[] ConvertToCartesian(double[] polar)
    {

        // Convert degrees to radian
        polar[1] = ConvertDegToRad(polar[1]);

        // Applying the formula:
        // x = rcos(theta), y = rsin(theta)
        double[] cartesian = { polar[0] * Math.cos(polar[1]), polar[0] * Math.sin(polar[1]) };

        // Print cartesian coordinates
        System.out.print(String.format("%.3f", cartesian[0])+" "+String.format("%.3f", cartesian[1]));
        return cartesian;

    }

}

// This code is contributed by offbeat
