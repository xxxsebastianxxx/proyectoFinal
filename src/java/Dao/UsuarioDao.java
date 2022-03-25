/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import models.UsuarioBean;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author ryzen3
 */
public class UsuarioDao {

    JdbcTemplate jdbcTemplate;
    ConectarDB con = new ConectarDB();

    public void borrarImagenUsuario(String fotousuario, String deletePath, int id_usuario) {
        this.jdbcTemplate = new JdbcTemplate(con.conDB());
        final String DELETE_DIRECTORY = "..\\..\\web\\";
        String deleteFile = deletePath + DELETE_DIRECTORY + fotousuario;
        System.out.print(deleteFile);
        File borrar = new File(deleteFile);

        if (borrar.delete()) {
            String sql = "delete from usuarios where id_usuario = ? ";
            jdbcTemplate.update(sql, id_usuario);
        } else {
            System.out.print("no se pudo borrar....");
        }

    }

    //--------actualizar usuario sin foto del usuario-------------------//
    public void actUsuarioSinfoto(UsuarioBean usuBean, List lista) {
        this.jdbcTemplate = new JdbcTemplate(con.conDB());
        ArrayList<String> listados = new ArrayList<>();
        for (int i = 0; i < lista.size(); i++) {
            FileItem fileItem = (FileItem) lista.get(i);
            listados.add(fileItem.getString());
        }
        usuBean.setNombre(listados.get(0));
        usuBean.setApellido(listados.get(1));
        usuBean.setTipo_de_identificacion(listados.get(2));
        usuBean.setIdentificacion(Integer.parseInt(listados.get(3)));
        usuBean.setDireccion(listados.get(4));
        //------------------------sql------------------------------//
        String sql = "update usuarios set nombre = ?, apellido = ?,  tipo_de_identificacion = ?, identificacion = ?, direccion = ? where id_usuario = ?";
        jdbcTemplate.update(sql, usuBean.getNombre(), usuBean.getApellido(),
                usuBean.getTipo_de_identificacion(), usuBean.getIdentificacion(),
                usuBean.getDireccion(), usuBean.getId_usuario());
    }

    private static final String UPLOAD_DIRECTORY = "..\\..\\web\\images\\photos";
    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3;
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40;

    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50;

    public void actUsuarioConImagen(UsuarioBean usuBean,
            boolean isMultipart,
            HttpServletRequest request,
            List items) {
        this.jdbcTemplate = new JdbcTemplate(con.conDB());
        ArrayList<String> listados = new ArrayList<>();
        if (isMultipart) {
            DiskFileItemFactory file = new DiskFileItemFactory();
            file.setSizeThreshold(MEMORY_THRESHOLD);
            file.setRepository(new File(System.getProperty("java.io.tmpdir")));
            ServletFileUpload fileUpload = new ServletFileUpload(file);
            fileUpload.setFileSizeMax(MAX_FILE_SIZE);
            fileUpload.setSizeMax(MAX_REQUEST_SIZE);
            String uploadPath = request.getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
            String deletePath = request.getServletContext().getRealPath("") + File.separator;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            for (int i = 0; i < items.size(); i++) {
                FileItem fileItem = (FileItem) items.get(i);
                if (!fileItem.isFormField()) {
                    String fileName = new File(fileItem.getName()).getName();
                    String filePath = uploadPath + File.separator + "ID-" + listados.get(0) + "-" + fileName;
                    File uploadFile = new File(filePath);
                    String nameFile = ("images/photos/" + "ID-" + listados.get(0) + "-" + fileName);
                    try {
                        borraImagenActualizada(usuBean.getFotoOld(), deletePath);
                        uploadFile.delete();
                        fileItem.write(uploadFile);
                        usuBean.setFotoUsuario(nameFile);
                    } catch (Exception e) {
                        System.out.print("escritura...." + e.getMessage());
                    }
                } else {
                    listados.add(fileItem.getString());
                }
            }
            usuBean.setNombre(listados.get(0));
            usuBean.setApellido(listados.get(1));
            usuBean.setTipo_de_identificacion(listados.get(2));
            usuBean.setIdentificacion(Integer.parseInt(listados.get(3)));
            usuBean.setDireccion(listados.get(4));
        }
        String sql = "update usuarios set nombre = ?, apellido = ?,  tipo_de_identificacion = ?, "
                + "identificacion = ?, direccion = ?, fotoUsuario = ? , fotoOld = ?  where id_usuario = ?";
        jdbcTemplate.update(sql, usuBean.getNombre(), usuBean.getApellido(),
                usuBean.getTipo_de_identificacion(), usuBean.getIdentificacion(),
                usuBean.getDireccion(), usuBean.getFotoUsuario(), usuBean.getFotoUsuario(), usuBean.getId_usuario());
    }

    public void borraImagenActualizada(String fotoUsuario, String deletePath) {
        final String DELETE_DIRECTORY = "..\\..\\web\\";

        String deleteFile = deletePath + DELETE_DIRECTORY + fotoUsuario;
        System.out.print(deleteFile);
        File borrar = new File(deleteFile);
        try {
            if (borrar.delete()) {
                System.out.print("borrado");
            }else{
                throw new Exception();
            }
        } catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

     //-------------------mostrar los clientes-----------------------------------
    public List consultarUsuariosAdopcion() {
        List datos = new ArrayList();
        this.jdbcTemplate = new JdbcTemplate(con.conDB());
        String sql = "select id_usuario, nombre from usuarios where limiteadopcion < 3";
        datos = this.jdbcTemplate.queryForList(sql);
        return datos;
    }

    public UsuarioBean consultarusuarioporid(int id) {
        UsuarioBean ub = new UsuarioBean();
        this.jdbcTemplate = new JdbcTemplate(con.conDB());
        String sql = "select * from usuarios where id_usuario =" + id;

        return (UsuarioBean) this.jdbcTemplate.query(
                sql, (ResultSet rs) -> {
                    if (rs.next()) {
                        ub.setId_usuario(rs.getInt("id_usuario"));
                        ub.setNombre(rs.getString("nombre"));
                        ub.setApellido(rs.getString("apellido"));
                        ub.setTipo_de_identificacion(rs.getString("tipo_de_identificacion"));
                        ub.setIdentificacion(rs.getInt("identificacion"));
                        ub.setDireccion(rs.getString("direccion"));
                        ub.setFotoUsuario(rs.getString("fotoUsuario"));
                    }
                    return ub;
                });
    }

    public List consultarusuariopornombre(String nombre) {
        List usu = new ArrayList();
        this.jdbcTemplate = new JdbcTemplate(con.conDB());
        String sql = "select * from usuarios where nombre = ? ";
        usu = this.jdbcTemplate.queryForList(sql, nombre);

        return usu;
    }

    public List consultarusuarioporapellido(String apellido) {
        List usu = new ArrayList();
        this.jdbcTemplate = new JdbcTemplate(con.conDB());
        String sql = "select * from usuarios where apellido = ? ";
        usu = this.jdbcTemplate.queryForList(sql, apellido);

        return usu;
    }

    public List consultarusuarioportipoid(String tipoid) {
        List usu = new ArrayList();
        this.jdbcTemplate = new JdbcTemplate(con.conDB());
        String sql = "select * from usuarios where tipo_de_identificacion = ? ";
        usu = this.jdbcTemplate.queryForList(sql, tipoid);

        return usu;
    }

    public List consultarusuarioporidentificacion(int identificacion) {
        List usu = new ArrayList();
        this.jdbcTemplate = new JdbcTemplate(con.conDB());
        String sql = "select * from usuarios where identificacion = ? ";
        usu = this.jdbcTemplate.queryForList(sql, identificacion);

        return usu;
    }

    public List consultarusuariopordireccion(String direccion) {
        List usu = new ArrayList();
        this.jdbcTemplate = new JdbcTemplate(con.conDB());
        String sql = "select * from usuarios where direccion  = ? ";
        usu = this.jdbcTemplate.queryForList(sql, direccion);

        return usu;
    }
}
