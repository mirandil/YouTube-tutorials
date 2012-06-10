package utility;

import de.matthiasmann.twl.utils.PNGDecoder;
import org.lwjgl.BufferUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.ARBTextureRectangle.GL_TEXTURE_RECTANGLE_ARB;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glBindTexture;

/**
 * Some useful methods for loading images.
 * @author Oskar Veerhoek
 */
public class ImagingTools {
    public static int glLoadTextureLinear(String location) {
        int texture = glGenTextures();
        glBindTexture(GL_TEXTURE_RECTANGLE_ARB, texture);
        InputStream in = null;
        try {
            in = new FileInputStream(location);
            PNGDecoder decoder = new PNGDecoder(in);
            ByteBuffer buffer = BufferUtils.createByteBuffer(4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buffer, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
            buffer.flip();
            in.close();
            glTexParameteri(GL_TEXTURE_RECTANGLE_ARB, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_RECTANGLE_ARB, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexImage2D(GL_TEXTURE_RECTANGLE_ARB, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        } catch (FileNotFoundException e) {
            System.err.println("Texture file could not be found.");
        } catch (IOException e) {
            System.err.print("Failed to load the texture file.");
        }
        glBindTexture(GL_TEXTURE_RECTANGLE_ARB, 0);
        return texture;
    }
}
