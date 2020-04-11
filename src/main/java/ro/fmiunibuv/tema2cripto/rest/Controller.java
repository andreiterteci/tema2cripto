package ro.fmiunibuv.tema2cripto.rest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;

@org.springframework.stereotype.Controller
public class Controller {
    ArrayList<BigInteger> bbsNumbers = new ArrayList<>();

    @GetMapping("/blum-blum-shub")
    public String mainPage(Model model){

        model.addAttribute("bitsize", "1024");
        model.addAttribute("userData", new UserData());
        model.addAttribute("bbsNumbers", bbsNumbers);

        return "blum-blum-shub";
    }

    @PostMapping("/blum-blum-shub")
    public String submit(@ModelAttribute UserData userData, Model model) throws IOException {
        if(userData.getFileSize() == null || userData.getFileSize() == 0){
            model.addAttribute("inputError", true);
            return "blum-blum-shub";
        }

        //radacina random pentru M initial
        bbsNumbers.clear();
        SecureRandom randomSeed = new SecureRandom();
        randomSeed.nextInt();
        if(userData.getBitSize() <= 3) {
            model.addAttribute("bitSizeMin", true);
            return "blum-blum-shub";
        }

        BigInteger M = BlumBlumShub.generateM(userData.getBitSize(), randomSeed);

        //radacina pentru primul xn
        byte[] seed = new byte[userData.getBitSize() / 8];
        randomSeed.nextBytes(seed);

        //creez primul xn
        BlumBlumShub bbs = new BlumBlumShub(M, seed);

        //creez urmatoarele xn bazate pe xn-1
        for (int i = 0; i < 1024 * userData.getFileSize(); ++i) {
            bbsNumbers.add(bbs.next(8));
        }

        //scrie in fisier
        writeBinaryFile(bbsNumbers);

        model.addAttribute("userData", new UserData());
        model.addAttribute("bbsNumbers", bbsNumbers);
        model.addAttribute("inputError", false);
        model.addAttribute("bitSizeMin", false);

        return "blum-blum-shub";
    }

    private void writeBinaryFile(ArrayList<BigInteger> bbsNumbers) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("/home/andrei/Facultate/criptografie/tema2cripto/src/main/resources/files/data.dat");
        BufferedOutputStream out = new BufferedOutputStream(fileOutputStream);
        for (BigInteger bbsNumber : bbsNumbers) {
            out.write(bbsNumber.byteValue());
        }
        out.flush();
        out.close();
    }
}
