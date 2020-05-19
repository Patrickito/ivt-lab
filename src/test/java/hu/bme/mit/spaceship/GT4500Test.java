package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore mosckTS1;
  private TorpedoStore mosckTS2;

  @BeforeEach
  public void init(){
    mosckTS1 = mock(TorpedoStore.class);
    mosckTS2 = mock(TorpedoStore.class);
    this.ship = new GT4500(mosckTS1, mosckTS2);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(mosckTS1.isEmpty()).thenReturn(false);
    when(mosckTS1.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);
    
    // Assert
    verify(mosckTS1, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(mosckTS1.isEmpty()).thenReturn(false);
    when(mosckTS2.isEmpty()).thenReturn(false);
    when(mosckTS1.fire(1)).thenReturn(true);
    when(mosckTS2.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mosckTS1, times(1)).fire(1);
    verify(mosckTS2, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_first_primary(){
    // Arrange
    when(mosckTS1.isEmpty()).thenReturn(false);
    when(mosckTS1.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mosckTS1, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_alternate(){
    // Arrange
    when(mosckTS1.isEmpty()).thenReturn(false);
    when(mosckTS2.isEmpty()).thenReturn(false);
    when(mosckTS1.fire(1)).thenReturn(true);
    when(mosckTS2.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mosckTS1, times(1)).fire(1);
    verify(mosckTS2, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_previous(){
    // Arrange
    when(mosckTS1.isEmpty()).thenReturn(false);
    when(mosckTS2.isEmpty()).thenReturn(true);
    when(mosckTS1.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mosckTS1, times(2)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_error(){
    // Arrange
    when(mosckTS1.isEmpty()).thenReturn(false);
    when(mosckTS2.isEmpty()).thenReturn(false);
    when(mosckTS1.fire(1)).thenReturn(false);
    when(mosckTS2.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
    verify(mosckTS1, times(1)).fire(1);
    verify(mosckTS2, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_All_both(){
    // Arrange
    when(mosckTS1.isEmpty()).thenReturn(false);
    when(mosckTS2.isEmpty()).thenReturn(false);
    when(mosckTS1.fire(1)).thenReturn(true);
    when(mosckTS2.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(mosckTS1, times(1)).fire(1);
    verify(mosckTS2, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_at_least_one(){
    // Arrange
    when(mosckTS1.isEmpty()).thenReturn(false);
    when(mosckTS2.isEmpty()).thenReturn(true);
    when(mosckTS1.fire(1)).thenReturn(true);
    when(mosckTS2.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(mosckTS1, times(1)).fire(1);
    verify(mosckTS2, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_all_empty(){
    // Arrange
    when(mosckTS1.isEmpty()).thenReturn(true);
    when(mosckTS2.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
    verify(mosckTS1, times(0)).fire(1);
    verify(mosckTS2, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_primary_empty(){
    // Arrange
    when(mosckTS1.isEmpty()).thenReturn(true);
    when(mosckTS2.isEmpty()).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
    verify(mosckTS1, times(0)).fire(1);
    verify(mosckTS2, times(1)).fire(1);
  }

  @Test
  public void laser(){
    // Arrange
    

    // Act
    boolean result = ship.fireLaser(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
  }

  @Test
  public void fireTorpedo_All_at_least_one2(){
    // Arrange
    when(mosckTS1.isEmpty()).thenReturn(true);
    when(mosckTS2.isEmpty()).thenReturn(false);
    when(mosckTS1.fire(1)).thenReturn(true);
    when(mosckTS2.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(mosckTS1, times(0)).fire(1);
    verify(mosckTS2, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_all_fail(){
    // Arrange
    when(mosckTS1.isEmpty()).thenReturn(true);
    when(mosckTS2.isEmpty()).thenReturn(true);
    when(mosckTS1.fire(1)).thenReturn(false);
    when(mosckTS2.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
  }

  @Test
  public void fireTorpedo_Single_twice_primary(){
    // Arrange
    when(mosckTS1.isEmpty()).thenReturn(false);
    when(mosckTS2.isEmpty()).thenReturn(true);
    when(mosckTS1.fire(1)).thenReturn(true);
    when(mosckTS2.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);
    when(mosckTS1.isEmpty()).thenReturn(true);
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mosckTS1, times(1)).fire(1);
    verify(mosckTS2, times(0)).fire(1);
  }


}
