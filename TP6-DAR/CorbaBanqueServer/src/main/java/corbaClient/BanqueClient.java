package corbaClient;

import corbaBanque.Compte;
import corbaBanque.IBanqueRemote;
import corbaBanque.IBanqueRemoteHelper;

import javax.naming.Context;
import javax.naming.InitialContext;

public class BanqueClient {
    public static void main(String[] args) {
        try {
            // a. Annuaire JNDI (lit jndi.properties)
            Context ctx = new InitialContext();

            // b. Récupérer la référence de l'objet distant
            org.omg.CORBA.Object obj = (org.omg.CORBA.Object) ctx.lookup("banque");

            // c. Convertir la référence vers le stub
            IBanqueRemote banque = IBanqueRemoteHelper.narrow(obj);

            // d. Tester chaque méthode
            System.out.println("=== 1. creerCompte ===");
            banque.creerCompte(new Compte(1, 1000f));
            banque.creerCompte(new Compte(2, 500f));
            System.out.println("Comptes 1 et 2 créés");

            System.out.println("=== 2. verser ===");
            banque.verser(200f, 1);
            System.out.println("Versement de 200 sur le compte 1 effectué");

            System.out.println("=== 3. retirer ===");
            banque.retirer(100f, 2);
            System.out.println("Retrait de 100 sur le compte 2 effectué");

            System.out.println("=== 4. getCompte ===");
            Compte c1 = banque.getCompte(1);
            System.out.println("Compte 1 : code=" + c1.code + " solde=" + c1.solde);

            System.out.println("=== 5. getComptes ===");
            for (Compte c : banque.getComptes()) {
                System.out.println("  code=" + c.code + " solde=" + c.solde);
            }

            System.out.println("=== 6. conversion ===");
            System.out.println("100 EUR = " + banque.conversion(100f) + " DT");

        } catch (Exception e) {
            System.err.println("Erreur client : " + e);
            e.printStackTrace();
        }
    }
}