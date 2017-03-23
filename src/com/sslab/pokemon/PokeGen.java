package com.sslab.pokemon;

import com.sslab.pokemon.data.PokemonIndividualData;
import com.sslab.pokemon.data.PokemonSpeciesData;
import com.sslab.pokemon.data.PokemonValueData;
import com.sslab.pokemon.sprite.PokemonSprite;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jerry on 2017/3/19.
 */
public class PokeGen {
    private JComboBox speciesComboBox;
    private JPanel root;

    private JButton deleteButton;
    private JButton saveButton;

    private JPanel slot0;
    private JPanel slot1;
    private JPanel slot2;
    private JPanel slot3;
    private JPanel slot4;
    private JPanel slot5;
    private JTextField nickNameField;
    private JTextField hpField;
    private JTextField atkField;
    private JTextField defField;
    private JTextField spAtkField;
    private JTextField spDefField;
    private JTextField speedField;
    private JPanel currentSelectedPanel;
    private ArrayList<JTextField> statField;

    Pokedex pokedex;
    HashMap<JPanel, PokemonIndividualData> pokemonMap;

    public PokeGen() {
        statField = new ArrayList<>();
        //TODO: Add the "stat" labels into statField
        statField.add(hpField);
        statField.add(atkField);
        statField.add(defField);
        statField.add(spAtkField);
        statField.add(spDefField);
        statField.add(speedField);
        //TODO: Use Pokedex to get pokemon species data
        pokedex=new Pokedex("bin/pokemonData.json");
        currentSelectedPanel=slot0;
        currentSelectedPanel.setBorder(BorderFactory.createBevelBorder(1));
        pokemonMap=new HashMap<>();
        //TODO: Add items into combobox. Each item should be a concat string of pokemon id and name from pokedex
        speciesComboBox.addItem("----------------");
        for(int i=0;i<pokedex.getPokemonSize();i++)
        {
            String str= Integer.toString(pokedex.getPokemonData(i).getId());
            str=str.concat(":");
            str=str.concat(pokedex.getPokemonData(i).getSpeciesName());
            speciesComboBox.addItem(str);
        }

        speciesComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO update fields when select items in combobox
                int id=speciesComboBox.getSelectedIndex();
                if(id>0)
                {
                    int [] arr=new int[6];
                    arr=pokedex.getPokemonData(id-1).getSpeciesValue().getValArray();
                    for(int i=0;i<6;i++)
                    {
                        statField.get(i).setText(Integer.toString(arr[i]));
                    }
                    JLabel currentLabel = (JLabel) currentSelectedPanel.getComponent(0);
                    setPokemonIcon(id - 1, currentLabel);

                }
                else
                {
                    for(int i=0;i<6;i++)
                    {
                        statField.get(i).setText("0");
                    }
                    nickNameField.setText("");
                    JLabel currentLabel = (JLabel) currentSelectedPanel.getComponent(0);
                    currentLabel.setIcon(null);
                }

            }
        });
        slot0.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                click(slot0);
            }
        });
        slot1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                click(slot1);
            }
        });
        slot2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                click(slot2);
            }
        });
        slot3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                click(slot3);
            }
        });
        slot4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                click(slot4);
            }
        });
        slot5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                click(slot5);
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPokemon(currentSelectedPanel);
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pokemonMap.remove(currentSelectedPanel);
                speciesComboBox.setSelectedIndex(0);
            }
        });
    }
    private void click(JPanel c)
    {
        if(pokemonMap.containsKey(currentSelectedPanel))
        {
            JLabel currentLabel = (JLabel) currentSelectedPanel.getComponent(0);
            setPokemonIcon(pokemonMap.get(currentSelectedPanel).getId() - 1, currentLabel);
        }
        currentSelectedPanel.setBorder(BorderFactory.createEtchedBorder());
        currentSelectedPanel=c;
        currentSelectedPanel.setBorder(BorderFactory.createBevelBorder(1));
        loadPokemon(currentSelectedPanel);

    }
    private void setPokemonIcon(int id,JLabel label)
    {
        ImageIcon icon = new ImageIcon(PokemonSprite.getSprite(id));
        label.setIcon(icon);
    }
    public void setPokemon(JPanel panel) {
        int id = speciesComboBox.getSelectedIndex();
        if(id>0)
        {
            int [] arr=new int[6];
            for(int i=0;i<6;i++)
            {
                arr[i]=Integer.parseInt(statField.get(i).getText());
            }
            PokemonValueData valueArray = new PokemonValueData(arr);
            String nick=nickNameField.getText();
            PokemonIndividualData pokemon=new PokemonIndividualData(id,nick,valueArray);
            pokemonMap.put(panel,pokemon);
        }

    }

    public void loadPokemon(JPanel panel) {
        if(pokemonMap.containsKey(panel))
        {

            PokemonIndividualData pokemon = pokemonMap.get(panel);
            speciesComboBox.setSelectedIndex(pokemon.getId());
            int[] arr = pokemon.getSpeciesValue().getValArray();
            for (int i = 0; i < 6; i++) {
                statField.get(i).setText(Integer.toString(arr[i]));
            }
            nickNameField.setText(pokemon.getNickName());
        }
        else
        {
            speciesComboBox.setSelectedIndex(0);
        }

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("PokeGen");
        frame.setContentPane(new PokeGen().root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}