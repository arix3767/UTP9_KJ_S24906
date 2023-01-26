package zad1;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TravelData {

    private static final Locale[] ALL_LOCALES = Locale.getAvailableLocales();
    private final List<List<String>> allTrips = new ArrayList<>();

    public TravelData(File dataDirectory) {
        if (!dataDirectory.exists() || !dataDirectory.isDirectory()) {
            throw new IllegalStateException("Incorrect File");
        }
        List<File> dataFiles = new ArrayList<>();
        File[] nestedFiles = dataDirectory.listFiles();
        if (nestedFiles == null) {
            throw new IllegalStateException("No file");
        }
        fillDataFilesRecursively(dataFiles, nestedFiles);

        try {
            for (File dataFile : dataFiles) {
                readFile(dataFile);
            }
        } catch (FileNotFoundException e) {
            System.out.println("---- FILE EXCEPTION --- CONSTR");
            e.printStackTrace();
        }

    }

    private void fillDataFilesRecursively(List<File> dataFiles, File[] nestedFiles) {
        for (File file : nestedFiles) {
            if (file == null) {
                continue;
            }
            if (file.isFile()) {
                dataFiles.add(file);
                continue;
            }
            if (file.isDirectory()) {
                Optional.ofNullable(file.listFiles())
                        .ifPresent(files -> fillDataFilesRecursively(dataFiles, files));
            }
        }
    }


    public List<String> getOffersDescriptionsList(String loc, String dateFormat) {
        List<String> results = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        Locale translationLocale = getLocale(loc);
        for (List<String> trip : allTrips) {
            Locale offerLocale = getLocale(trip.get(0));
            Locale.setDefault(offerLocale);
            Locale toTranslate = getLocaleToTranslate(trip.get(1));

            StringBuilder result = new StringBuilder();
            result.append(toTranslate.getDisplayCountry(translationLocale));

            try {
                Date dateFrom = simpleDateFormat.parse(trip.get(2));
                result.append("	").append(simpleDateFormat.format(dateFrom));

                Date dateTo = simpleDateFormat.parse(trip.get(3));
                result.append("	").append(simpleDateFormat.format(dateTo));

                ResourceBundle destination = ResourceBundle.getBundle("zad1.translate", translationLocale);
                result.append("	").append(destination.getString(trip.get(4)));

                Number price = NumberFormat.getInstance(offerLocale).parse(trip.get(5));
                result.append("	").append(NumberFormat.getInstance(offerLocale).format(price));

                Currency currency = Currency.getInstance(trip.get(6));
                result.append("	").append(currency.getCurrencyCode());

                results.add(result.toString());
            } catch (ParseException e) {
                System.out.println("--- PARSE EXCEPTION ---");
            }
        }
        return results;
    }

    private Locale getLocaleToTranslate(String countryName) {
        for (Locale locale : ALL_LOCALES) {
            if (locale.getDisplayCountry().equals(countryName))
                return locale;
        }
        return Locale.getDefault();
    }

    private Locale getLocale(String loc) {
        String[] znaki = loc.split("_");
        if (znaki.length > 2) {
            throw new IllegalStateException("Wrong Locale");
        }
        if (znaki.length > 1) {
            return new Locale(znaki[0], znaki[1]);
        }
        return new Locale(znaki[0]);
    }

    private void readFile(File file) throws FileNotFoundException {
        Scanner read = new Scanner(file);
        while (read.hasNextLine()) {
            String line = read.nextLine();
            String[] words = line.split("\\t");
            allTrips.add(Arrays.asList(words));
        }
    }
}