import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import pojos.Contabilidad;
import pojos.Empresa;

import java.util.Arrays;
import java.util.regex.Pattern;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Updates.*;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * @author Nando💦
 */
public class Main {
    private static MongoClient client;
    private static MongoDatabase db;

    /**
     * Initializes the MongoDB client and the DB, able to manage POJOs
     */
    public static void init() {
        String dbName = "abastos";
        client = new MongoClient();

        CodecRegistry pojoCodecRegistry =
                fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                        fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        db = client.getDatabase(dbName)
                .withCodecRegistry(pojoCodecRegistry);
        System.out.printf("[+] Connected to the DB => %s\n", dbName);
    }

    /**
     * Closes the MongoClient
     */
    public static void close() {
        client.close();
        System.out.println("\n[+] Client has been closed");
    }

    /**
     * drops the collection "empresas"
     */
    public static void dropCollection() {
        db.getCollection("empresas", Empresa.class).drop();
        System.out.println("\t[+] Collection \"empresas\" dropped\n");
    }

    /**
     * Inserts an article to the DB
     */
    public static void insertCompany(Empresa empresa) {
        MongoCollection<Empresa> empresas = db.getCollection("empresas", Empresa.class);
        empresas.insertOne(empresa);

        System.out.printf("[+] Company added => %s\n", empresa.getNombre());
    }

    public static FindIterable<Empresa> findServiceAndDomain(String service, String domain) {
        MongoCollection<Empresa> empresas = db.getCollection("empresas", Empresa.class);

        return empresas.find(
                and(
                        eq("servicios", service),
                        regex("web", Pattern.compile(domain))
                )
        );
    }

    public static FindIterable<Empresa> findMoreXEmployees(int cant) {
        MongoCollection<Empresa> empresas = db.getCollection("empresas", Empresa.class);

        return empresas.find(
                gt("contabilidad.n_empleados", cant)
        );
    }

    public static Empresa mostFacturation() {
        MongoCollection<Empresa> empresas = db.getCollection("empresas", Empresa.class);

        return empresas.find().sort(descending("contabilidad.facturacion")).first();
    }

    public static void decrementGastos(int cant) {
        MongoCollection<Empresa> empresas = db.getCollection("empresas", Empresa.class);
        empresas.updateMany(
                eq("servicios", "web"),
                inc("contabilidad.gastos", -cant)
        );
    }

    public static void removeFieldWeb(int cant) {
        MongoCollection<Empresa> empresas = db.getCollection("empresas", Empresa.class);
        empresas.updateMany(
                lt("contabilidad.facturacion", cant),
                unset("web")
        );
    }

    public static void addFieldTeamBuilding(int cant) {
        MongoCollection<Empresa> empresas = db.getCollection("empresas", Empresa.class);
        empresas.updateMany(
                gt("contabilidad.n_empleados", cant),
                push("servicios", "team buiding")
        );
    }

    public static void dropCompaniesWhithoutWeb() {
        MongoCollection<Empresa> empresas = db.getCollection("empresas", Empresa.class);
        empresas.deleteMany(
                exists("web", false)
        );
    }

    public static void main(String[] args) {
        init();
        dropCollection();

        Empresa empresa1 = new Empresa(
                "Software SA",
                "B111",
                "www.software.sa",
                Arrays.asList("aplicaciones", "web"),
                new Contabilidad(3, 10000, 10000)
        );
        Empresa empresa2 = new Empresa(
                "Apps SL",
                "B222",
                "www.apps.com",
                Arrays.asList("aplicaciones", "móviles", "web"),
                new Contabilidad(135, 520000, 450000)
        );
        Empresa empresa3 = new Empresa(
                "Startup SL",
                "B333",
                "www.startup.com",
                Arrays.asList("aplicaciones", "loquefaçafalta"),
                new Contabilidad(10, 110000, 250000)
        );
        Empresa empresa4 = new Empresa(
                "Currantes SA",
                "B444",
                "www.currantes.org",
                Arrays.asList("móviles", "procesos", "hilos"),
                new Contabilidad(22, 58000, 45000)
        );
        insertCompany(empresa1);
        insertCompany(empresa2);
        insertCompany(empresa3);
        insertCompany(empresa4);

        System.out.println("\n###### Service and Domain ######");
        FindIterable<Empresa> empresasFiltered = findServiceAndDomain("aplicaciones", ".com");
        for (Empresa empresa : empresasFiltered)
            System.out.println(empresa);

        System.out.println("\n####### More than 5 employees #######");
        FindIterable<Empresa> moreThan5 = findMoreXEmployees(5);
        for (Empresa empresa : moreThan5)
            System.out.println(empresa);

        System.out.println("\n###### The Most Facturation ######");
        System.out.println(mostFacturation());

        System.out.println("\n###### Decrement Gastos Web ######");
        decrementGastos(10000);

        System.out.println("\n###### Remove field web ######");
        removeFieldWeb(60000);

        System.out.println("\n###### Add service Team Building ######");
        addFieldTeamBuilding(100);

        System.out.println("\n###### Remove Companies Whithout Web of the DB ######");
        dropCompaniesWhithoutWeb();

        close(); //❤
    }
}
