/**
 * @author Thomas Drake-Brockman
**/

interface TCCommand {
  void run(TCSocket socket) throws TCCommandException;
}