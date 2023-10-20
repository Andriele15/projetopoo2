package DAO;

import DTO.UsuarioDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class UsuarioDAO {

    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;
    ArrayList<UsuarioDTO> lista = new ArrayList<>();//Utilizado na pesquisa de usuário

    public ResultSet autenticacaoUsuario(UsuarioDTO objusuariodto) {
        conn = new ConexaoDAO().conectarBD();

        try {
            String sql = "select * from tbusuario where login_usuario = ? and senha_usuario = ?";

            pstm = conn.prepareStatement(sql);
            pstm.setString(1, objusuariodto.getNome_usuario());
            pstm.setString(2, objusuariodto.getSenha_usuario());

            rs = pstm.executeQuery();
            return rs;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "UsuarioDAO: " + e);
            return null;
        }
    }

    //Cadastrar Usuário
    public void cadastrarUsuario(UsuarioDTO objUsuarioDTO) {
        //Inserir dados no banco/ Lembrando escreva de acordo com seu banco de dados 
        String sql = "insert into tbusuario (login_usuario, senha_usuario) values(?, ?)";

        //Novo objeto de conexão 
        conn = new ConexaoDAO().conectarBD();

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, objUsuarioDTO.getNome_usuario());
            pstm.setString(2, objUsuarioDTO.getSenha_usuario());

            pstm.execute();
            pstm.close();
            //Aviso para sucesso de cadastro 
        } catch (SQLException e) {
            JOptionPane.showInternalMessageDialog(null, "NovoUsuarioDAO" + e);
        }
    }

    //Listar usuários 
    public ArrayList<UsuarioDTO> PesquisarUsuario() {

        //Código que busca as informações do banco 
        String sql = "Select * from tbusuario";

        conn = new ConexaoDAO().conectarBD();

        try {
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            //Enquanto houver informações ele continua lendo, quando acabar ele para
            while (rs.next()) {

                UsuarioDTO objUsuarioDTO = new UsuarioDTO();
                objUsuarioDTO.setId_usuario(rs.getInt("idtbusuario"));// Atributo Id_usuario(Tua função do DTO) tem que ser igual o do banco
                objUsuarioDTO.setNome_usuario(rs.getString("login_usuario"));// Atributo Nome_usuario (Tua função do DTO) tem que ser igual o do banco
                objUsuarioDTO.setSenha_usuario(rs.getString("senha_usuario"));// Atributo Senha_usuario (Tua função do DTO) tem que ser igual o do banco

                lista.add(objUsuarioDTO);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "UsuarioDAO" + e);
        }
        return lista;
    }
//Alterar usuário
    public void AlterarUsuario(UsuarioDTO objUsuarioDTO) {
        String sql = "update tbusuario set login_usuario = ?, senha_usuario = ? where idtbusuario = ?";

        conn = new ConexaoDAO().conectarBD();

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, objUsuarioDTO.getNome_usuario());
            pstm.setString(2, objUsuarioDTO.getSenha_usuario());
            pstm.setInt(3, objUsuarioDTO.getId_usuario());
            pstm.execute();
            pstm.close();
            JOptionPane.showMessageDialog(null, "Usuário e Senha Editados com sucesso!!!");
        }catch (SQLException e){ 
        JOptionPane.showMessageDialog(null, "NovoUsuarioDAO Editar:" + e);
        }
    }
    
//Excluir Usuário
    public void ExcluirUsuario(UsuarioDTO objUsuarioDTO){ 
    String sql = "delete from tbusuario where idtbusuario = ?";
    conn = new ConexaoDAO().conectarBD();
    
    try{ 
    pstm = conn.prepareStatement(sql);
    pstm.setInt(1, objUsuarioDTO.getId_usuario());
    pstm.execute();
    pstm.close();
    JOptionPane.showMessageDialog(null,"Usuário excluido com sucesso!");
    }catch(SQLException e){ 
    JOptionPane.showMessageDialog(null,"NovoUsuarioDAO Excluir:" + e);
    }
    }
    
}
