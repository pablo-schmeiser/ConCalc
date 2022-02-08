/*
 * Copyright (C) 2022, Pablo Schmeiser.
 *
 * This software is released under the terms of the
 * GNU GPL-3.0 license. See http://www.gnu.org/licenses/gpl-3.0.html
 * for more information.
 */

package concalc;

/**
 * Calculates and prints the users condom size, using a given diameter.
 * Offers different command line arguments, such as -h/--help/help to print the help and
 * -n/--numeric/-d/--durex/-b/--billyboy/-r/--ritex to specify the format of the size to calculate.
 * Given no argument, main will print all formats to the command line.
 *
 * Also, able to deal with faulty inputs.
 *
 * @author Pablo Schmeiser
 * @version 1.0.0
 */
public final class ConCalc {
    //Constants:
    private static final String HELP = "help";
    private static final String HELP1 = "--help";
    private static final String H = "-h";
    private static final double LOWER_BOUND = 9.6;
    private static final double UPPER_BOUND = 14.4;
    private static final String NUMERIC_TEXT = " is your condom size!";
    private static final String DUREX_TEXT = " is your Durex condom size!";
    private static final String BILLY_BOY_TEXT = " should be your fitting ";
    private static final String RITEX_TEXT = " should be your fitting Ritex condom size!";
    private static final String ILLEGAL_ARG_STRING = "Illegal argument was used!";
    private static final String DIAMETER_MISSING = "Diameter needed for calculation!";
    private static final String TIPP = "Try using the -h argument to print the help!";
    private static final String INVALID_COMMAND = "The command you used is invalid!";
    private static final int MIN_SIZE = 47;
    private static final double STEP = 0.3;
    private static final int STANDARD_SIZE = 53;
    private static final int DUREX_UPPER = 56;
    private static final String STANDARD = "STANDARD";
    private static final String GROSS = "GROSS";
    private static final int BILLY_BOY_LOWER = 52;
    private static final int RITEX_UPPER = 54;
    private static final String EXTRA_GROSS = "EXTRA GROSS";
    private static final String NONE = "None";
    private static final String STANDARD1 = "Standard";
    private static final String EXTRA_GR_OSS = "Extra Groß";
    private static final String XXL = "XXL";
    private static final String DESCRIPTION
        = "This program calculates your condom size and accepts one of the following arguments: ";
    private static final String HELP_NUMERIC
        = "\n\t -h/--help/help \t\t\t This help page \n\t -n/--numeric \t\t\t numeric format ";
    private static final String DUREX_BILLY_BOY
        = "\n\t -d/--durex \t\t\t Durex format \n\t -b/--billyboy \t\t\t BillyBoy format ";
    private static final String RITEX = "\n\t -r/--ritex \t\t\t Ritex format";
    private static final String INVALID_SIZE = "No valid size found for entered diameter!";
    private static final String BILLY_BOY_CONDOM_SIZE_STRING = "Billy Boy condom size!";
    private static final String ILLEGAL_STATE = "ConCalc may only be used with the diameter and one argument!";
    private static final String N = "-n";
    private static final String NUMERIC = "--numeric";
    private static final String DUREX = "--durex";
    private static final String D = "-d";
    private static final String B = "-b";
    private static final String BILLY_BOY = "--billyboy";
    private static final String R = "-r";
    private static final String RITEX_COND = "--ritex";

    private ConCalc() {
        //Utility class
    }

    /**
     * The main method, called upon execution.
     *
     * @param args System arguments.
     */
    public static void main(String[] args) {
        try {
            if (args.length >= 1) {
                if (args.length == 1) {
                    if (args[0].equals(HELP) || args[0].equals(H) || args[0].equals(HELP1)) {
                        help();
                    } else {
                        double diameter = Double.parseDouble(args[0]);
                        if (diameter < LOWER_BOUND || diameter > UPPER_BOUND) {
                            throw new IllegalStateException(INVALID_SIZE);
                        }

                        //Executes calculation for all formats if no specifications are given.
                        System.out.println(calc(diameter) + NUMERIC_TEXT);
                        System.out.println(toDurex(calc(diameter)) + DUREX_TEXT);
                        System.out.println(toBillyBoy(calc(diameter)) + BILLY_BOY_TEXT + BILLY_BOY_CONDOM_SIZE_STRING);
                        System.out.println(toRitex(calc(diameter)) + RITEX_TEXT);
                    }
                } else {
                    double diameter = Double.parseDouble(args[0]);
                    if (diameter < LOWER_BOUND || diameter > UPPER_BOUND) {
                        throw new IllegalStateException(INVALID_SIZE);
                    }

                    if (args.length > 2) {
                        throw new IllegalStateException(ILLEGAL_STATE);
                    }
                    switch (args[1]) {
                        case N:
                        case NUMERIC:
                            //Executes calculation for numeric format.
                            System.out.println(calc(diameter) + NUMERIC_TEXT);
                            break;
                        case D:
                        case DUREX:
                            //Executes calculation for Durex format.
                            System.out.println(toDurex(calc(diameter)) + DUREX_TEXT);
                            break;
                        case B:
                        case BILLY_BOY:
                            //Executes calculation for Billy Boy format.
                            System.out.println(toBillyBoy(calc(diameter)) + BILLY_BOY_TEXT
                                + BILLY_BOY_CONDOM_SIZE_STRING);
                            break;
                        case R:
                        case RITEX_COND:
                            //Executes calculation for Ritex format.
                            System.out.println(toRitex(calc(diameter)) + RITEX_TEXT);
                            break;
                        default:
                            throw new IllegalStateException(ILLEGAL_ARG_STRING);
                    }
                }
            } else {
                throw new IllegalStateException(DIAMETER_MISSING);
            }
        } catch (IllegalStateException exception) {
            System.err.println(exception.getMessage());
            System.out.println(TIPP);
        } catch (NumberFormatException exception) {
            System.err.println(INVALID_COMMAND);
            System.out.println(TIPP);
        }
    }

    private static int calc(double diam) {
        double calcVar = diam - LOWER_BOUND;
        int returnValue = MIN_SIZE;

        while (calcVar > 0.0) {
            calcVar -= STEP;
            returnValue++;
        }

        return returnValue;
    }

    private static String toDurex(int size) {
        if (size < STANDARD_SIZE) {
            return STANDARD;
        } else if (size <= DUREX_UPPER) {
            return GROSS;
        } else {
            return EXTRA_GROSS;
        }
    }

    private static String toBillyBoy(int size) {
        if (size < BILLY_BOY_LOWER) {
            return NONE;
        } else if (size <= STANDARD_SIZE) {
            return STANDARD1;
        } else {
            return EXTRA_GR_OSS;
        }
    }

    private static String toRitex(int size) {
        if (size < STANDARD_SIZE) {
            return NONE;
        } else if (size <= RITEX_UPPER) {
            return STANDARD1;
        } else {
            return XXL;
        }
    }

    private static void help() {
        System.out.print(DESCRIPTION + HELP_NUMERIC + DUREX_BILLY_BOY + RITEX);
    }

}
